package com.test.gitsubs.ui.enter_user_name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.gitsubs.GitApp
import com.test.gitsubs.R
import com.test.gitsubs.data.GitRepository
import com.test.gitsubs.databinding.FragmentEnterLoginNameBinding
import com.test.gitsubs.ui.user_followers.FollowerListViewDirections
import com.test.gitsubs.utils.extentions.obtainViewModel
import com.test.gitsubs.utils.extentions.viewModelFactory
import javax.inject.Inject

class EnterUserLoginNameView : Fragment() {

    private lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var repository: GitRepository

    private fun initViewModel() {
        val factory = viewModelFactory {
            UserViewModel(repository)
        }
        userViewModel = obtainViewModel(UserViewModel::class.java, factory)
    }

    private fun initUI() {
        binding.etUserName.setText(getString(R.string.startUserNickName))

        binding.btnSearch.setOnClickListener {
            val nickName = binding.etUserName.text.toString()

            userViewModel.user.observe(
                viewLifecycleOwner
            ) {
                it.data?.let { user -> navigateToFollowersList(user.login) }
            }
            userViewModel.getUserData(nickName)
        }
    }

    private fun navigateToFollowersList(userLoginName: String) {
        val direction =
            FollowerListViewDirections.actionEnterUserToFollowerListFragment(userLoginName)

        findNavController().navigate(direction)
    }

    //region fragment lifecycle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initUI()
    }

    private var _binding: FragmentEnterLoginNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DI
        GitApp.component?.injectEnterLoginView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterLoginNameBinding.inflate(inflater, container, false)
        return binding.root
    }
    //endregion

}