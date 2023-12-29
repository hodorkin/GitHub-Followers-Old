package com.test.gitsubs.data

data class UserDomainModel(
    val id: String,
    val login: String,
    val name: String,
    val avatarUrl: String,

    val type: String,
    val nodeId: String,
    val twitterUsername: String,
    val followers: String,
    val following: String
)
