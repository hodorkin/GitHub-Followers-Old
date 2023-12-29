package com.test.gitsubs.data.api

import com.test.gitsubs.data.api.entities.FollowerApiModel
import com.test.gitsubs.data.api.entities.UserApiModel
import com.test.gitsubs.data.api.entities.wrapper.Data

interface GitApiSource {

    suspend fun getUser(userNickName: String): Data<UserApiModel>

    suspend fun getUserFollowers(userNickName: String): Data<List<FollowerApiModel>>

}