package com.test.gitsubs.di

import android.content.Context
import com.test.gitsubs.data.GitRepository
import com.test.gitsubs.data.GitUserRepository
import com.test.gitsubs.data.api.GitApiGateway
import com.test.gitsubs.data.api.remote.GitApiServiceFactory
import com.test.gitsubs.data.db.GitUserDatabase
import dagger.Module
import dagger.Provides

@Module
class StorageModule(private val context: Context) {

    @Provides
    fun provideGitRepository(): GitRepository {
        return GitUserRepository.getInstance(
            GitApiGateway.getInstance(GitApiServiceFactory.getService()),
            GitUserDatabase.getInstance(context).gitUserDao()
        )
    }

}