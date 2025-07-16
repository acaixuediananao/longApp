package com.newangle.healthy.pages.user.list

import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.bean.User
import com.newangle.healthy.databinding.FragmentUserListBinding
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

class UserListFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val fragmentComponent by lazy {
        (requireActivity().application as NewAngleApp).appComponent.fragmentComponent().create()
    }

    private val buttonClick = { user:User -> LogUtils.i("click user info $user")}
    private val deleteClick = { user:User ->
        LogUtils.i("click delete user info $user")
    }

    @Inject
    lateinit var viewModel: UserListViewModel
    lateinit var binding: FragmentUserListBinding
    private val adapter = UserListAdapter(buttonClick = buttonClick, deleteClick = deleteClick)

    override fun onAttach(context: Context) {
        fragmentComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
        // TODO: Use the ViewModel
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    when(value) {
                        is UiState.FAILED -> {
                            showError(value.msg)
                        }
                        is UiState.LOADING -> {

                        }
                        is UiState.SUCCESS -> {
                            adapter.refresh(value.data)
                        }
                        is UiState.INIT -> {

                        }
                    }
                }
            }
        }
        viewModel.loadUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        with(binding){
            userListRv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = this@UserListFragment.adapter
            }
        }
    }
}