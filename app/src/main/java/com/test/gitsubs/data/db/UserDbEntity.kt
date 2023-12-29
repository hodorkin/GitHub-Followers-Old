package com.test.gitsubs.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index("login")]
)
data class UserDbEntity(
    @ColumnInfo(name = "id")
    val id: String,

    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "nodeId")
    val nodeId: String,

    @ColumnInfo(name = "twitter_username")
    val twitterUsername: String,

    @ColumnInfo(name = "followers")
    val followersCount: String,

    @ColumnInfo(name = "following")
    val followingCount: String
)