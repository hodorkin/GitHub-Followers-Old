package com.test.gitsubs.data

import com.test.gitsubs.data.api.GitApiGateway
import com.test.gitsubs.data.api.GitApiSource
import com.test.gitsubs.data.api.entities.FollowerApiModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import com.test.gitsubs.data.db.GitDaoSource
import com.test.gitsubs.data.db.UserDbEntity

/*
Repository strategy:
1:
- getUser always perform online call and returns full info about User - so it can be save it repo always
- getFollowers returns only some info about user
So:
- for every followerUser should be check - is that user already in cash - and then complete domain model in result
 */

class GitUserRepository(
    private val apiSrc: GitApiSource,
    private val daoSource: GitDaoSource
) : GitRepository {

     override suspend fun getUser(userNickName: String): Data<UserDomainModel> {
         val cashedUser = daoSource.getUser(userNickName)
         return if (cashedUser == null) {
             val dataResult = apiSrc.getUser(userNickName)

             val user = dataResult.data?.toDomainModel
             if (user != null) {
                 daoSource.insertOrUpdate(user.toDaoModel)
             }

             Data.success(user)
        } else {
            Data.success(cashedUser.toDomainModel)
        }
    }

    override suspend fun getUserFollowers(userNickName: String): Data<List<UserDomainModel>> {
        val dataResult = apiSrc.getUserFollowers(userNickName)

        val userList = dataResult.data?.map {
            replaceFollowerWithUser(
                it,
                daoSource.getUser(it.login)
            )
        }

        // TODO handle error cases here
        return Data.success(userList)
    }

    private fun replaceFollowerWithUser(
        follower: FollowerApiModel,
        user: UserDbEntity?
    ): UserDomainModel {

        if (user == null) return follower.toUserDomainModel

        return if (follower.login == user.login)
            user.toDomainModel
        else
            follower.toUserDomainModel
    }

    companion object {
        private var INSTANCE: GitRepository? = null

        fun getInstance(
            apiSrc: GitApiSource,
            daoSource: GitDaoSource
        ): GitRepository = INSTANCE
            ?: synchronized(GitApiGateway::class.java) {
                GitUserRepository(
                    apiSrc,
                    daoSource
                )
                    .also { INSTANCE = it }
            }
    }
}