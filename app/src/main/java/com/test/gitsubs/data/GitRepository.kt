package com.test.gitsubs.data

import com.test.gitsubs.data.api.entities.wrapper.Data

interface GitRepository {

    suspend fun getUser(userNickName: String): Data<UserDomainModel>

    suspend fun getUserFollowers(userNickName: String): Data<List<UserDomainModel>>

}