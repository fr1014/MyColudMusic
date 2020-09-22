package com.fr1014.mycoludmusic.app;

import com.fr1014.mycoludmusic.data.DataRepository;
import com.fr1014.mycoludmusic.data.source.http.ApiService;
import com.fr1014.mycoludmusic.http.RetrofitClient;
import com.fr1014.mymvvm.base.BaseApplication;

/**
 * 创建时间:2020/9/4
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class MyApplication extends BaseApplication {

    public static DataRepository provideRepository() {
        //网络服务api
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        //数据仓库
        return DataRepository.getInstance(apiService);
    }
}
