package com.test.gitsubs.ui.user_followers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.gitsubs.data.GitRepository
import com.test.gitsubs.data.UserDomainModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import kotlinx.coroutines.launch

class UserFollowersViewModel(
    private val repository: GitRepository,
    private val userNickName: String
) : ViewModel() {

    val user = MutableLiveData<Data<UserDomainModel>>()
    val followers = MutableLiveData<Data<List<UserDomainModel>>>()

    init {
        viewModelScope.launch {
            user.postValue(repository.getUser(userNickName))
        }

        viewModelScope.launch {
            followers.postValue(repository.getUserFollowers(userNickName))
        }
    }

}
