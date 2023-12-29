package com.test.gitsubs.data

import com.test.gitsubs.data.api.entities.FollowerApiModel
import com.test.gitsubs.data.api.entities.UserApiModel
import com.test.gitsubs.data.db.UserDbEntity

internal val UserApiModel.toDomainModel: UserDomainModel
    get() = UserDomainModel(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        name = name ?: "",
        type = type,
        nodeId = node_id,
        twitterUsername = twitter_username ?: "",
        followers = followers,
        following = following
    )

internal val FollowerApiModel.toUserDomainModel: UserDomainModel
    get() = UserDomainModel(
        id = id,
        login = login,
        avatarUrl = avatar_url,
        name = "",
        type = "",
        nodeId = "",
        twitterUsername = "",
        followers = "",
        following = ""
    )

internal val UserDomainModel.toDaoModel: UserDbEntity
    get() = UserDbEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        name = name,
        type = type,
        nodeId = nodeId,
        twitterUsername = twitterUsername,
        followersCount = followers,
        followingCount = following
    )

internal val UserDbEntity.toDomainModel: UserDomainModel
    get() = UserDomainModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        name = name,
        type = type,
        nodeId = nodeId,
        twitterUsername = twitterUsername,
        followers = followersCount,
        following = followingCount
    )