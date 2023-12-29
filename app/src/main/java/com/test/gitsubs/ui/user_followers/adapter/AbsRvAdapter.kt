package com.test.gitsubs.ui.user_followers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.test.gitsubs.R
import com.test.gitsubs.data.api.entities.wrapper.Data
import com.test.gitsubs.data.api.entities.wrapper.DataState

abstract class AbsRvAdapter<T>(
    val context: Context?,
    var data: Data<List<T>>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract fun createDataViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    open var errorMessage = context?.getString(R.string.error_data_load)

    fun submitData(listData: Data<List<T>>) {
        listData.let {
            data = it
            notifyDataSetChanged()
            if (listData.status == DataState.ERROR) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    open fun createLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.data_state_loading_list, parent, false)
        )
    }

    open fun createErrorViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ErrorItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.data_state_error_list, parent, false)
        )
    }

    open fun createEmptyViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EmptyItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.data_state_empty_list, parent, false)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DATA -> createDataViewHolder(parent)
            TYPE_LOADING -> createLoadingViewHolder(parent)
            TYPE_ERROR -> createErrorViewHolder(parent)
            TYPE_EMPTY -> createEmptyViewHolder(parent)
            else -> error("Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        if (data.data.isNullOrEmpty()) {
            return when (data.status) {
                DataState.LOADING,
                DataState.ERROR,
                DataState.SUCCESS,
                DataState.EMPTY -> 1
            }
        }

        return data.data?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        if (data.data.isNullOrEmpty()) {
            return when (data.status) {
                DataState.LOADING -> TYPE_LOADING
                DataState.ERROR -> TYPE_ERROR
                DataState.SUCCESS -> TYPE_EMPTY
                DataState.EMPTY -> TYPE_EMPTY
            }
        }

        return TYPE_DATA
    }

    companion object {
        const val TYPE_DATA = 0
        const val TYPE_ERROR = 1
        const val TYPE_LOADING = 2
        const val TYPE_EMPTY = 3
    }

    inner class LoadingItem(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class ErrorItem(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class EmptyItem(itemView: View) : RecyclerView.ViewHolder(itemView)
}