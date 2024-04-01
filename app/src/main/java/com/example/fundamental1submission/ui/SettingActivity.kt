package com.example.fundamental1submission.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.fundamental1submission.R
import com.example.fundamental1submission.SettingPreferences
import com.example.fundamental1submission.databinding.ActivitySettingBinding
import com.example.fundamental1submission.viewModel.SettingThemeViewModel
import com.example.fundamental1submission.viewModel.SettingThemeViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.Setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingThemeViewModelFactory(pref)
        )[SettingThemeViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this)
        { isDarkModeActive: Boolean ->
            setNightModeSum(isDarkModeActive)
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchNightmode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchNightmode.isChecked = false
            }
        }

        binding.switchNightmode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
            setNightModeSum(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setNightModeSum(isNightMode: Boolean) {
        binding.tvNightMode.text =
            if (isNightMode) resources.getString(R.string.DarkModeOff) else resources.getString(
                R.string.DarkModeOn
            )
    }

}