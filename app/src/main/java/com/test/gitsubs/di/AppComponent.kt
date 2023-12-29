package com.test.gitsubs.di

import com.test.gitsubs.ui.enter_user_name.EnterUserLoginNameView
import com.test.gitsubs.ui.user_followers.FollowerListView
import dagger.Component

@Component(
    modules = [
        StorageModule::class
    ]
)
interface AppComponent {

    fun injectEnterLoginView(view: EnterUserLoginNameView)

    fun injectFollowersView(view: FollowerListView)

}