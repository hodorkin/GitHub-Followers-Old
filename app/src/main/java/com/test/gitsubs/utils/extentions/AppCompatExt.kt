package com.test.gitsubs.utils.extentions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> Fragment.obtainViewModel(
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
): T {
    return ViewModelProvider(this, viewModelFactory).get(viewModelClass)
}

inline fun <T> viewModelFactory(crossinline create: () -> T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return create() as T
        }
    }
}

