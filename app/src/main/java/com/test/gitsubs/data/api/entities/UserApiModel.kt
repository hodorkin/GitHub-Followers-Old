package com.test.gitsubs.data.api.entities

import com.squareup.moshi.Json


/**
 * This is API model. It should be used only in request context
 */
data class UserApiModel(
    @Json(name = "login")
    val login: String,

    @Json(name = "id")
    val id: String,

    @Json(name = "node_id")
    val node_id: String,

    @Json(name = "avatar_url")
    val avatar_url: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "name")
    val name: String?,

    @Json(name = "twitter_username")
    val twitter_username: String?,

    @Json(name = "followers")
    val followers: String,

    @Json(name = "following")
    val following: String
)
