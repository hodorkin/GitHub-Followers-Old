package com.test.gitsubs.data.api

import com.test.gitsubs.data.api.entities.FollowerApiModel
import com.test.gitsubs.data.api.entities.UserApiModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import com.test.gitsubs.data.api.remote.GitApi
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

class GitApiGateway(
    private val gitApi: GitApi
) : GitApiSource {

    override suspend fun getUser(userNickName: String): Data<UserApiModel> {
        return try {
            val response = gitApi.getUserData(userNickName)
            val userApiModelData: UserApiModel? = response.body()

            if (response.isSuccessful) {
                Timber.d("data fetched :%s", userApiModelData)
                Data.success(userApiModelData)
            } else {
                Data.empty()
            }
        } catch (e: SocketTimeoutException) { // this is crap
            Timber.e(e, "SocketTimeoutException")
            Data.empty()
        }
    }

    override suspend fun getUserFollowers(userNickName: String): Data<List<FollowerApiModel>> {
        var list: List<FollowerApiModel>? = emptyList()
        try {
            val response = gitApi.getUserFollowers(userNickName)
            list = response.body()

            return if (response.isSuccessful) {
                Timber.d("data fetched size:%s", list?.size)
                val isListNotEmpty = list?.isNotEmpty() ?: false
                if (isListNotEmpty) {
                    Data.success(list)
                } else {
                    Data.empty()
                }
            } else {
                Timber.e("response fail: %s", response.toString())
                Data.error(response.message(), list)
            }
        } catch (e: SocketTimeoutException) { // this is crap
            Timber.e(e, "SocketTimeoutException")
            return Data.exception("SocketTimeoutException", list)
        } catch (e: HttpException) {
            Timber.e(e, "HttpException")
            return Data.exception("HttpException", list)
        } catch (e: Throwable) {
            Timber.e(e, "Throwable")
            return Data.exception("Throwable", list)
        }
    }

    companion object {
        private var INSTANCE: GitApiGateway? = null

        fun getInstance(
            gitApi: GitApi
        ): GitApiGateway = INSTANCE
            ?: synchronized(GitApiGateway::class.java) {
                GitApiGateway(gitApi)
                    .also { INSTANCE = it }
            }
    }
}