package com.newangle.healthy.pages.user.list

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.databinding.ActivityUserListBinding

class UserListActivity : BaseActivity() {
    lateinit var binding: ActivityUserListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addUserListFragment()
    }


    private fun  addUserListFragment() {
        supportFragmentManager.commit {
            add(R.id.user_list_container, UserListFragment.newInstance())
        }
    }

}