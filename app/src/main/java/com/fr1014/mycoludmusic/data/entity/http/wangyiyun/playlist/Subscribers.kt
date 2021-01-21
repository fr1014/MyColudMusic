package com.fr1014.mycoludmusic.data.entity.http.wangyiyun.playlist

data class Subscribers(
        val defaultAvatar: Boolean,
        val province: Int,
        val authStatus: Int,
        val followed: Boolean,
        val avatarUrl: String,
        val accountStatus: Int,
        val gender: Int,
        val city: Int,
        val birthday: Long,
        val userId: Long,
        val userType: Int,
        val nickname: String,
        val signature: String,
        val description: String,
        val detailDescription: String,
        val avatarImgId: Long,
        val backgroundImgId: Long,
        val backgroundUrl: String,
        val authority: Int,
        val mutual: Boolean,
        val expertTags: Any,
        val experts: Any,
        val djStatus: Int,
        val vipType: Int,
        val remarkName: Any,
        val avatarImgIdStr: String,
        val backgroundImgIdStr: String,
        val avatarImgId_str: String
)