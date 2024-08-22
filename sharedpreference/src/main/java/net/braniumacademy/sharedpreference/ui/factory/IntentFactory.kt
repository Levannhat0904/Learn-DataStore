package net.braniumacademy.sharedpreference.ui.factory

import android.content.Intent

interface IntentFactory<T> {
    fun createIntent(actionType: IntentActionType, obj: T?): Intent
}