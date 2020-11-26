package com.fr1014.mycoludmusic.data.source.http;

import com.fr1014.mycoludmusic.entity.wangyiyun.CheckEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.PlayListDetailEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.SearchEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.SongDetailEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.SongUrlEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.TopListDetailEntity;
import com.fr1014.mycoludmusic.entity.wangyiyun.TopListEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * 创建时间:2020/9/4
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public interface HttpDataSource {
 /*
  ========================网易云===================================
*/
    Observable<TopListEntity> getTopList();

    Observable<TopListDetailEntity> getTopListDetail();

    Observable<PlayListDetailEntity> getPlayListDetail(long id);

    Observable<SongDetailEntity> getSongDetail(long ids);

    Observable<CheckEntity> checkMusic(long id);

    Observable<SongUrlEntity> getSongUrl(long id);

    Observable<SearchEntity> getSearch(String keywords, int offset);

/*
===============================酷我========================================
 */
    Observable<com.fr1014.mycoludmusic.entity.kuwo.SearchEntity> getSearch(String name, int page,int count);
    Observable<ResponseBody> getSongUrl(String rid);
}
