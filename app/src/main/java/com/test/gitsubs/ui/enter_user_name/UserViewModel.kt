package com.test.gitsubs.ui.enter_user_name

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.gitsubs.data.GitRepository
import com.test.gitsubs.data.UserDomainModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: GitRepository
) : ViewModel() {

    val user = MutableLiveData<Data<UserDomainModel>>()

    fun getUserData(userNickName: String) {
        viewModelScope.launch {
            user.postValue(repository.getUser(userNickName))
        }
    }

}