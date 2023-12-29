package com.test.gitsubs.data.api.remote

import com.test.gitsubs.data.api.entities.FollowerApiModel
import com.test.gitsubs.data.api.entities.UserApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {

    // "https://api.github.com/users/{user}",
    @GET("users/{userLogin}")
    suspend fun getUserData(@Path("userLogin") userLogin: String): Response<UserApiModel>

    // "followers_url": "https://api.github.com/user/followers",
    @GET("users/{userLogin}/followers")
    suspend fun getUserFollowers(@Path("userLogin") userLogin: String): Response<List<FollowerApiModel>>

}