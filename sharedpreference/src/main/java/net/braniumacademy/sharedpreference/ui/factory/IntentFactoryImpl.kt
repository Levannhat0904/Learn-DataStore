package net.braniumacademy.sharedpreference.ui.factory

import android.content.Context
import android.content.Intent
import net.braniumacademy.sharedpreference.data.User
import net.braniumacademy.sharedpreference.ui.detail.UserDetailActivity
import net.braniumacademy.sharedpreference.ui.newuser.NewUserActivity
import net.braniumacademy.sharedpreference.ui.setting.SettingsActivity
import net.braniumacademy.sharedpreference.ui.updateuser.UpdateUserActivity

class IntentFactoryImpl<T>(private val context: Context) : IntentFactory<T> {
    override fun createIntent(actionType: IntentActionType, obj: T?): Intent {
        return when (actionType) {
            IntentActionType.CREATE -> createIntentToAddNew()
            IntentActionType.UPDATE -> createIntentToUpdate(obj)
            IntentActionType.DETAIL -> createIntentToViewDetail(obj)
            IntentActionType.SETTINGS -> createIntentToSetting()
        }
    }

    private fun createIntentToAddNew(): Intent {
        return Intent(context, NewUserActivity::class.java)
    }

    private fun createIntentToUpdate(obj: T?): Intent {
        return Intent(context, UpdateUserActivity::class.java).apply {
            val user = obj as? User
            putExtra(UpdateUserActivity.EXTRA_ID, user?.id)
        }
    }

    private fun createIntentToViewDetail(obj: T?): Intent {
        return Intent(context, UserDetailActivity::class.java).apply {
            val user = obj as? User
            putExtra(UserDetailActivity.EXTRA_ID, user?.id)
        }
    }

    private fun createIntentToSetting(): Intent {
        return Intent(context, SettingsActivity::class.java)
    }
}