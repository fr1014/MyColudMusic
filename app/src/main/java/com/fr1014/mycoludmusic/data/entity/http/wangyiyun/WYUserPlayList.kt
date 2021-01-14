package com.fr1014.mycoludmusic.data.entity.http.wangyiyun

/**
 * Create by fanrui on 2021/1/13
 * Describe:
 */
data class WYUserPlayList(
    val code: Int,
    val more: Boolean,
    val playlist: List<Playlist>,
    val version: String
)

data class Playlist(
    val adType: Int,
    val anonimous: Boolean,
    val artists: Any,
    val backgroundCoverId: Int,
    val backgroundCoverUrl: Any,
    val cloudTrackCount: Int,
    val commentThreadId: String,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val createTime: Long,
    val creator: Creator,
    val description: Any,
    val englishTitle: Any,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val newImported: Boolean,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val playCount: Int,
    val privacy: Int,
    val recommendInfo: Any,
    val specialType: Int,
    val status: Int,
    val subscribed: Boolean,
    val subscribedCount: Int,
    val subscribers: List<Any>,
    val tags: List<String>,
    val titleImage: Int,
    val titleImageUrl: Any,
    val totalDuration: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: Any,
    val updateFrequency: Any,
    val updateTime: Long,
    val userId: Int
)