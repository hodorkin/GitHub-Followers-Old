package com.test.gitsubs.data.api.entities.wrapper

data class Data<out T>(val status: DataState, val data: T?, val message: String?) {
    companion object {

        fun <T> loading(data: T?): Data<T> {
            return Data(DataState.LOADING, data, null)
        }

        fun <T> success(data: T?): Data<T> {
            return Data(DataState.SUCCESS, data, null)
        }

        fun <T> empty(): Data<T> {
            return Data(DataState.EMPTY, null, null)
        }

        fun <T> error(msg: String, data: T?): Data<T> {
            return Data(DataState.ERROR, data, msg)
        }

        fun <T> exception(msg: String, data: T?): Data<T> {
            return Data(DataState.ERROR, data, msg)
        }
    }
}