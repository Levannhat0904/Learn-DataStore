package net.braniumacademy.sharedpreference.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import net.braniumacademy.sharedpreference.R
import net.braniumacademy.sharedpreference.data.User
import net.braniumacademy.sharedpreference.ui.adapter.UserAdapter
import net.braniumacademy.sharedpreference.databinding.ActivityMainBinding
import net.braniumacademy.sharedpreference.ui.dialog.DeleteDialogFragment
import net.braniumacademy.sharedpreference.ui.factory.IntentActionType
import net.braniumacademy.sharedpreference.ui.factory.IntentFactoryImpl
import net.braniumacademy.sharedpreference.ui.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupRecyclerView()
        setupFloatActionButton()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupRecyclerView() {
        binding.recyclerUser.layoutManager = LinearLayoutManager(this)
        val itemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        getDrawable(R.drawable.divider)?.let { itemDecoration.setDrawable(it) }
        binding.recyclerUser.addItemDecoration(itemDecoration)
        binding.recyclerUser.adapter = UserAdapter(
            viewModel.users.value!!,
            object : OptionMenuClickListener {
                override fun update(user: User) {
                    val intent = IntentFactoryImpl<User>(baseContext)
                        .createIntent(IntentActionType.UPDATE, user)
                    startActivity(intent)
                }

                override fun delete(user: User) {
                    DeleteDialogFragment(user)
                        .show(supportFragmentManager, "TAG")
                }

                override fun viewDetail(user: User) {
                    val intent = IntentFactoryImpl<User>(baseContext)
                        .createIntent(IntentActionType.DETAIL, user)
                    startActivity(intent)
                }
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewModel() {
        viewModel = UserViewModel.getInstance()
        viewModel.users.observe(this) {
            binding.recyclerUser.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupFloatActionButton() {
        binding.fbtnAdd.setOnClickListener {
            val intent = IntentFactoryImpl<User>(baseContext)
                .createIntent(IntentActionType.CREATE, null)
            startActivity(intent)
        }
        // open setting screen
        binding.fbtnSetting.setOnClickListener {
            val intent = IntentFactoryImpl<User>(baseContext)
                .createIntent(IntentActionType.SETTINGS, null)
            startActivity(intent)
        }
    }

    interface OptionMenuClickListener {
        fun update(user: User)
        fun delete(user: User)
        fun viewDetail(user: User)
    }
}