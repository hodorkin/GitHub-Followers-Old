package com.test.gitsubs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BASE = "users"

@Database(
    entities = [UserDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GitUserDatabase : RoomDatabase() {

    abstract fun gitUserDao(): GitDaoSource

    // classic singleton in Kotlin
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GitUserDatabase? = null

        fun getInstance(context: Context): GitUserDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GitUserDatabase {
            return Room
                .databaseBuilder(context, GitUserDatabase::class.java, DATA_BASE)
                .build()
        }
    }

}