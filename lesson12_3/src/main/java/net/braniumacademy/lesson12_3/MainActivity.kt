package net.braniumacademy.lesson12_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.braniumacademy.lesson12_3.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter: ObservableInt = ObservableInt(0)
    private lateinit var counterKey: Preferences.Key<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        counterKey = intPreferencesKey(getString(R.string.saved_counter_key))
        loadSavedData()
        setButtonListener()
    }

    private fun saveData() {
        lifecycleScope.launch {
            dataStore.edit { values ->
                values[counterKey] = counter.get()
            }
        }
    }

    private fun loadSavedData() {
        val counterFlow: Flow<Int> = dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[counterKey] ?: 0
        }
        lifecycleScope.launch {
            counterFlow.first {
                counter.set(it)
                true
            }
        }
        binding.counter = counter
    }

    private fun setButtonListener() {
        binding.btnDecrease.setOnClickListener {
            try {
                val factor = binding.editFactor.text.toString().toInt()
                counter.set(counter.get() - factor)
                saveData()
            } catch (_: NumberFormatException) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.text_input_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        binding.btnIncrease.setOnClickListener {
            try {
                val factor = binding.editFactor.text.toString().toInt()
                counter.set(counter.get() + factor)
                saveData()
            } catch (_: NumberFormatException) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.text_input_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}