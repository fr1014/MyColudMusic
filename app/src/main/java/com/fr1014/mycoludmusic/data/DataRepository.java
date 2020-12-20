package com.fr1014.mycoludmusic.data;

import androidx.lifecycle.LiveData;

import com.fr1014.mycoludmusic.data.entity.http.kuwo.KWNewSearchEntity;
import com.fr1014.mycoludmusic.data.entity.http.kuwo.KWSearchEntity;
import com.fr1014.mycoludmusic.data.entity.http.kuwo.KWSongDetailEntity;
import com.fr1014.mycoludmusic.data.entity.http.kuwo.KWSongInfoAndLrcEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.WYSongLrcEntity;
import com.fr1014.mycoludmusic.data.entity.room.MusicEntity;
import com.fr1014.mycoludmusic.data.source.http.HttpDataSource;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.CheckEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.PlayListDetailEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.WYSearchEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.SongDetailEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.SongUrlEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.TopListDetailEntity;
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.TopListEntity;
import com.fr1014.mycoludmusic.data.source.local.room.LocalDataSource;
import com.fr1014.mymvvm.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 创建时间:2020/9/4
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class DataRepository extends BaseModel implements HttpDataSource, LocalDataSource {

    private volatile static DataRepository instance = null;
    private HttpDataSource httpDataSource;
    private LocalDataSource localDataSource;

    private DataRepository(HttpDataSource httpDataSource, LocalDataSource localDataSource) {
        this.httpDataSource = httpDataSource;
        this.localDataSource = localDataSource;
    }

    public static DataRepository getInstance(HttpDataSource httpDataSource, LocalDataSource localDataSource) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(httpDataSource, localDataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public Observable<ResponseBody> getSongCover(String coverPath) {
        return httpDataSource.getSongCover(coverPath);
    }

    @Override
    public Observable<TopListEntity> getTopList() {
        return httpDataSource.getTopList();
    }

    @Override
    public Observable<TopListDetailEntity> getTopListDetail() {
        return httpDataSource.getTopListDetail();
    }

    @Override
    public Observable<PlayListDetailEntity> getTopList(long id) {
        return httpDataSource.getTopList(id);
    }

    @Override
    public Observable<PlayListDetailEntity> getPlayListDetail(long id) {
        return httpDataSource.getPlayListDetail(id);
    }

    @Override
    public Observable<SongDetailEntity> getSongDetail(long ids) {
        return httpDataSource.getSongDetail(ids);
    }

    public Observable<SongUrlEntity> getWYSongUrl(String ids) {
        return httpDataSource.getWYSongUrl(ids);
    }

    public Observable<SongUrlEntity> getWYSongUrl(long id) {
        return httpDataSource.getWYSongUrl(id);
    }

    @Override
    public Observable<CheckEntity> checkMusic(long id) {
        return httpDataSource.checkMusic(id);
    }

    @Override
    public Observable<WYSearchEntity> getSearch(String keywords, int offset) {
        return httpDataSource.getSearch(keywords, offset);
    }

    @Override
    public Observable<WYSongLrcEntity> getWYSongLrcEntity(long id) {
        return httpDataSource.getWYSongLrcEntity(id);
    }

//    @Override
//    public Observable<KWSearchEntity> getSearch(String name, int page, int count) {
//        return httpDataSource.getSearch(name, page, count);
//    }

    public Observable<ResponseBody> getKWSongUrl(String rid) {
        return httpDataSource.getKWSongUrl(rid);
    }

    @Override
    public Observable<ResponseBody> getSearchResult(String name, int count) {
        return httpDataSource.getSearchResult(name, count);
    }

    @Override
    public Observable<KWNewSearchEntity> getKWSearchResult(String name, int page, int count) {
        return httpDataSource.getKWSearchResult(name, page, count);
    }

    @Override
    public Observable<KWSongDetailEntity> getKWSongDetail(long mid) {
        return httpDataSource.getKWSongDetail(mid);
    }

    @Override
    public Observable<KWSongInfoAndLrcEntity> getKWSongInfoAndLrc(String mid) {
        return httpDataSource.getKWSongInfoAndLrc(mid);
    }

    @Override
    public LiveData<List<MusicEntity>> getAllLive() {
        return localDataSource.getAllLive();
    }

    @Override
    public LiveData<List<MusicEntity>> getAllHistoryOrCurrentLive(boolean history) {
        return localDataSource.getAllHistoryOrCurrentLive(history);
    }

    @Override
    public LiveData<MusicEntity> getItemLive(String title, String artist) {
        return localDataSource.getItemLive(title, artist);
    }

    @Override
    public List<MusicEntity> getAll() {
        return localDataSource.getAll();
    }

    @Override
    public List<MusicEntity> getAllHistoryOrCurrent(boolean history) {
        return localDataSource.getAllHistoryOrCurrent(history);
    }

    @Override
    public MusicEntity getItem(String title, String artist,boolean isHistory) {
        return localDataSource.getItem(title, artist,isHistory);
    }

    @Override
    public void insertAll(List<MusicEntity> musicEntities) {
        localDataSource.insertAll(musicEntities);
    }

    @Override
    public void insert(MusicEntity musicEntity) {
        localDataSource.insert(musicEntity);
    }

    @Override
    public void delete(MusicEntity musicEntity) {
        localDataSource.delete(musicEntity);
    }
}
