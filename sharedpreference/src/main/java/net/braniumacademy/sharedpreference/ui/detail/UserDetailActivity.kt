package net.braniumacademy.sharedpreference.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.braniumacademy.sharedpreference.databinding.ActivityUserDetailBinding
import net.braniumacademy.sharedpreference.ui.viewmodel.UserViewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fillData()
        setAction()
    }

    private fun fillData() {
        val userId = intent.getIntExtra(EXTRA_ID, 0)
        val user = UserViewModel.getInstance().getUser(userId)
        binding.editEmailDetail.setText(user?.email)
        binding.editNameDetail.setText(user?.name)
    }

    private fun setAction() {
        binding.btnOk.setOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_ID = "net.braniumacademy.sharedpreference.ui.detail.EXTRA_ID"
    }
}