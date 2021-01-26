package com.fr1014.mycoludmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fr1014.mycoludmusic.app.AppViewModelFactory;
import com.fr1014.mycoludmusic.app.MyApplication;
import com.fr1014.mycoludmusic.base.BasePlayActivity;
import com.fr1014.mycoludmusic.customview.PlayStatusBarView;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.user.Profile;
import com.fr1014.mycoludmusic.data.entity.room.MusicEntity;
import com.fr1014.mycoludmusic.data.source.local.room.DBManager;
import com.fr1014.mycoludmusic.databinding.ActivityMainBinding;
import com.fr1014.mycoludmusic.eventbus.LoginStatusEvent;
import com.fr1014.mycoludmusic.musicmanager.Music;
import com.fr1014.mycoludmusic.musicmanager.Preferences;
import com.fr1014.mycoludmusic.musicmanager.QuitTimer;
import com.fr1014.mycoludmusic.musicmanager.listener.OnPlayerEventListener;
import com.fr1014.mycoludmusic.ui.SwitchDialogFragment;
import com.fr1014.mycoludmusic.musicmanager.AudioPlayer;
import com.fr1014.mycoludmusic.ui.search.SearchActivity;
import com.fr1014.mycoludmusic.utils.CollectionUtils;
import com.fr1014.mycoludmusic.utils.CommonUtils;
import com.fr1014.mycoludmusic.utils.CoverLoadUtils;
import com.fr1014.mycoludmusic.utils.ScreenUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BasePlayActivity<ActivityMainBinding, MainViewModel> implements View.OnClickListener, SwitchDialogFragment.MusicSourceCallback {
    private static final int REQUEST_PERMISSION_CODE = 100;
    private AppBarConfiguration mAppBarConfiguration;
    private PlayStatusBarView statusBar;
    private Toast toast;
    private OnPlayerEventListener playEventListener;

    @Override
    public void musicSource(int position) {
        if (toast != null) {
            toast.cancel();
        }
        String source = SwitchDialogFragment.array[position];
        toast = CommonUtils.toastShort("音乐源已切换为: " + source);
        mViewBinding.appBarMain.tvSearch.setText("当前搜索源：" + source);
        switch (position) {
            case 0:  //酷我
                SourceHolder.get().setSource("酷我");
                break;
            case 1:  //网易
                SourceHolder.get().setSource("网易");
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public MainViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(MyApplication.getInstance());
        return new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mViewBinding.appBarMain.toolbar);
        mViewBinding.appBarMain.toolbar.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(mViewBinding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(mViewBinding.navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.playListDetailFragment) {
                    mViewBinding.appBarMain.toolbar.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.topListFragment) {
                    mViewBinding.appBarMain.toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    mViewBinding.appBarMain.toolbar.setVisibility(View.VISIBLE);
                    mViewBinding.appBarMain.llContent.setVisibility(View.GONE);
                } else if (destination.getId() == R.id.userInfoFragment) {
                    mViewBinding.appBarMain.toolbar.setVisibility(View.GONE);
                } else {
                    mViewBinding.appBarMain.toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mViewBinding.appBarMain.toolbar.setVisibility(View.VISIBLE);
                    mViewBinding.appBarMain.llContent.setVisibility(View.VISIBLE);
                }
            }
        });
        initToolbar();
        initCustomNavView();
        initClickListener();
    }

    private void initCustomNavView() {
        mViewBinding.navView.setViewModel(mViewModel,this);
        initNavHeaderViewData(getUserProfile());
    }

    private void initNavHeaderViewData(Profile profile) {
        mViewBinding.navView.initNavHeaderViewData(profile);
    }

    private void initToolbar() {
        mViewBinding.appBarMain.tvSearch.setText("当前搜索源：" + SourceHolder.get().getSource());
    }

    private void initClickListener() {
        mViewBinding.appBarMain.ivSwitch.setOnClickListener(this);
        mViewBinding.appBarMain.tvSearch.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStringEvent(LoginStatusEvent event) {
        if (event.isLogin) {
            Profile profile = getUserProfile();
            initNavHeaderViewData(profile);
            if (profile != null) {
                mViewModel.getLikeIdList(profile.getUserId());
            }
        }
    }

    private Profile getUserProfile() {
        return Preferences.getUserProfile();
    }

    //首次绑定Service时该方法被调用
    @Override
    protected void onServiceBound() {
        QuitTimer.get().setOnTimerListener(mViewBinding.navView);
        DBManager.get().getLocalMusicList(false).observe(this, new Observer<List<MusicEntity>>() {
            @Override
            public void onChanged(List<MusicEntity> musicEntities) {
                if (statusBar == null) {
                    if (!CollectionUtils.isEmptyList(musicEntities)) {
                        List<Music> musicList = new ArrayList<>();
                        for (MusicEntity musicEntity : musicEntities) {
                            long id = 0L;
                            if (musicEntity.getId() != null) {
                                id = musicEntity.getId();
                            }
                            musicList.add(new Music(id, musicEntity.getArtist(), musicEntity.getTitle(), "", musicEntity.getImgUrl(), musicEntity.getMusicRid(), musicEntity.getDuration()));
                        }
                        AudioPlayer.get().addMusicList(musicList);
                    }
                    statusBar = new PlayStatusBarView(MainActivity.this, getSupportFragmentManager());
                    playEventListener = statusBar.getOnPlayEventListener();
                    if (playEventListener != null) {
                        AudioPlayer.get().addOnPlayEventListener(playEventListener);
                    }
                    CoverLoadUtils.get().registerLoadListener(statusBar);
                    mViewBinding.appBarMain.contentMain.flPlaystatus.addView(statusBar);
                }

                if (statusBar.getVisibility() != View.VISIBLE && !CollectionUtils.isEmptyList(musicEntities)) {
                    statusBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initData() {
        requestMyPermissions();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_switch:
                new SwitchDialogFragment().show(getSupportFragmentManager(), "switch_dialog");
                break;
            case R.id.tv_search:
                startActivity(SearchActivity.class);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        //维护当前播放列表
//        mViewModel.delOldInsertNewMusicList(AudioPlayer.get().getMusicList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (statusBar != null) {
            CoverLoadUtils.get().removeLoadListener(statusBar);
        }
        if (playEventListener != null) {
            AudioPlayer.get().removeOnPlayEventListener(playEventListener);
        }
    }

    private void requestMyPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
//            for (int i = 0; i < permissions.length; i++) {
//                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
//            }
        }
    }
}