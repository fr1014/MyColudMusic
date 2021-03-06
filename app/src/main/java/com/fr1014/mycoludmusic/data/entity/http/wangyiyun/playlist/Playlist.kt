package com.fr1014.mycoludmusic.data.entity.http.wangyiyun.playlist

data class Playlist(
        val adType: Int,
        val anonimous: Boolean,
        val artists: Any,
        val backgroundCoverId: Long,
        val backgroundCoverUrl: Any,
        val cloudTrackCount: Int,
        val commentThreadId: String,
        val coverImgId: Long,
        val coverImgId_str: String,
        val coverImgUrl: String,
        val createTime: Long,
        val creator: Creator,
        val description: String,
        val englishTitle: Any,
        val highQuality: Boolean,
        val id: Long,
        val name: String,
        val newImported: Boolean,
        val opRecommend: Boolean,
        val ordered: Boolean,
        val playCount: Long,
        val privacy: Int,
        val recommendInfo: Any,
        val specialType: Int,
        val status: Int,
        val subscribed: Boolean,
        val subscribedCount: Long,
        val subscribers: List<Subscribers>,
        val tags: List<String>,
        val titleImage: Long,
        val titleImageUrl: Any,
        val totalDuration: Int,
        val trackCount: Int,
        val trackNumberUpdateTime: Long,
        val trackUpdateTime: Long,
        val tracks: List<Track>,
        val trackIds:List<TrackIds>,
        val updateFrequency: Any,
        val updateTime: Long,
        val userId: Long,
        val commentCount: Long,
        val shareCount: Long
)