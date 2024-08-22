package net.braniumacademy.sharedpreference.ui.updateuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.braniumacademy.sharedpreference.R
import net.braniumacademy.sharedpreference.databinding.ActivityUpdateUserBinding
import net.braniumacademy.sharedpreference.ui.dialog.UpdateDialogFragment
import net.braniumacademy.sharedpreference.ui.viewmodel.UserViewModel

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateUserBinding
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.containerLayout.btnAdd.text = getString(R.string.text_btn_update)
        fillData(intent)
        addListeners()
    }

    private fun addListeners() {
        binding.containerLayout.btnAdd.setOnClickListener {
            val name = binding.containerLayout.editName.text.toString()
            val email = binding.containerLayout.editEmail.text.toString()
            UpdateDialogFragment(object : UpdateUserListener {
                override fun onUpdate() {
                    val viewMode = UserViewModel.getInstance()
                    viewMode.updateUser(userId, name, email)
                    finish()
                }

                override fun onCancel() {
                    finish()
                }
            }).show(supportFragmentManager, "TAG")
        }
        binding.containerLayout.btnCancel.setOnClickListener { finish() }
    }

    private fun fillData(intent: Intent) {
        userId = intent.getIntExtra(EXTRA_ID, 0)
        val user = UserViewModel.getInstance().getUser(userId)
        binding.containerLayout.editName.setText(user?.name)
        binding.containerLayout.editEmail.setText(user?.email)
    }

    interface UpdateUserListener {
        fun onUpdate()
        fun onCancel()
    }

    companion object {
        const val EXTRA_ID = "net.braniumacademy.sharedpreference.ui.updateuser.EXTRA_ID"
    }
}