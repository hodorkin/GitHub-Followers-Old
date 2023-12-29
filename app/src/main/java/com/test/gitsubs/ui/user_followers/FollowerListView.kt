package com.test.gitsubs.ui.user_followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.test.gitsubs.GitApp
import com.test.gitsubs.R
import com.test.gitsubs.data.GitRepository
import com.test.gitsubs.data.UserDomainModel
import com.test.gitsubs.data.api.entities.wrapper.Data
import com.test.gitsubs.databinding.FragmentUserListExtBinding
import com.test.gitsubs.ui.user_followers.adapter.FollowerAdapter
import com.test.gitsubs.utils.extentions.obtainViewModel
import com.test.gitsubs.utils.extentions.viewModelFactory

import javax.inject.Inject

class FollowerListView : Fragment() {

    private lateinit var viewModelFollowers: UserFollowersViewModel

    @Inject
    lateinit var repository: GitRepository

    private fun initUserInfo() {
        viewModelFollowers.user.observe(viewLifecycleOwner, { userData ->
            userData.data?.let { setUserData(it) }
        })
    }

    private fun initFollowersList() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        binding.listRv.adapter = FollowerAdapter(Data.loading(null), requireContext()) {
            goToNext(it.login)
        }

        viewModelFollowers.followers.observe(viewLifecycleOwner, { data -> // , Observer { data ->
            (binding.listRv.adapter as FollowerAdapter).submitData(data)
        })
    }

    private fun getUserNickName(): String =
        FollowerListViewArgs.fromBundle(requireArguments()).userNickName

    private fun setUserData(user: UserDomainModel) {
        binding.toolbarTitle.title = user.name
        binding.toolbarTitle.invalidate()

//        binding.userStatTv.text = user.followers + " / " + user.following
        binding.userStatTv.text =
            resources.getString(R.string.user_stats_prefix, user.followers, user.following)
        binding.userNickNameTv.text =
            resources.getString(R.string.user_nick_name_prefix, user.login)
        Picasso.get().load(user.avatarUrl).into(binding.userAvatarIv)
    }

    private fun goToNext(userLoginName: String) {
        val direction =
            FollowerListViewDirections.actionEnterUserToFollowerListFragment(userLoginName)

        findNavController().navigate(direction)
    }

    private fun initViewModel() {
        val factory = viewModelFactory {
            UserFollowersViewModel(
                repository,
                getUserNickName()
            )
        }
        viewModelFollowers = obtainViewModel(UserFollowersViewModel::class.java, factory)
    }

    //region fragment lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        initUserInfo()
        initFollowersList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitApp.component?.injectFollowersView(this)
    }

    private var _binding: FragmentUserListExtBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListExtBinding.inflate(inflater, container, false)
        return binding.root
    }
    //endregion

}