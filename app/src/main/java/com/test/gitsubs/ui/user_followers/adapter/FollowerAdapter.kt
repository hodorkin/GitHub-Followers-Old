package com.test.gitsubs.ui.user_followers.adapter

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.gitsubs.R
import com.test.gitsubs.data.UserDomainModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import com.test.gitsubs.databinding.ItemUserBinding

class FollowerAdapter(
    resource: Data<List<UserDomainModel>>,
    context: Context?,
    private val clickListener: (UserDomainModel) -> Unit
) :
    AbsRvAdapter<UserDomainModel>(context, resource) {

    override fun createDataViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return UserItem(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserItem) {
            holder.bind(data.data?.get(position), clickListener)
        }
    }

    inner class UserItem(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemUserBinding.bind(itemView)

        fun bind(userItem: UserDomainModel?, clickListener: (UserDomainModel) -> Unit) {
            userItem?.let { user ->
                with(itemView) {
                    if (user.avatarUrl.isNotEmpty()) {
                        Picasso.get().load(user.avatarUrl).into(binding.userIconIv)
                    }
                    binding.userLoginNameTv.text = user.login
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        itemView.stateListAnimator =
                            AnimatorInflater.loadStateListAnimator(
                                this.context,
                                R.animator.lift_on_touch
                            )
                    }
                    setOnClickListener {
                        clickListener(user)
                    }
                }
            }
        }
    }

}