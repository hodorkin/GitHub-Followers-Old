package com.test.gitsubs.data.db

import androidx.room.*

@Dao
interface GitDaoSource {

    @Query("SELECT * FROM users WHERE login = :userNickName")
    suspend fun getUser(userNickName: String): UserDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserDbEntity)

    @Delete
    suspend fun deleteUser(user: UserDbEntity)

    @Query("DELETE FROM users")
    fun deleteAll()
}