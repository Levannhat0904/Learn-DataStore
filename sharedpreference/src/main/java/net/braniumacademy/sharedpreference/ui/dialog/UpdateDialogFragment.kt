package net.braniumacademy.sharedpreference.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.braniumacademy.sharedpreference.R
import net.braniumacademy.sharedpreference.ui.updateuser.UpdateUserActivity

class UpdateDialogFragment(
    private val listener: UpdateUserActivity.UpdateUserListener
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(getString(R.string.text_confirm_update))
        dialog.setPositiveButton(getString(R.string.text_confirm)) { _, _ ->
            listener.onUpdate()
        }
        dialog.setNegativeButton(getString(R.string.text_cancel)) { _, _ ->
            listener.onCancel()
        }
        return dialog.create()
    }
}
