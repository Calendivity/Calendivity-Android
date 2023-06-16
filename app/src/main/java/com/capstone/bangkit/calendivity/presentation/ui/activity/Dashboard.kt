package com.capstone.bangkit.calendivity.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.bangkit.calendivity.R
import com.capstone.bangkit.calendivity.databinding.ActivityDashboardBinding
import com.capstone.bangkit.calendivity.presentation.ui.fragment.CalendarFragment
import com.capstone.bangkit.calendivity.presentation.ui.fragment.GroupFragment
import com.capstone.bangkit.calendivity.presentation.ui.fragment.HomeFragment
import com.capstone.bangkit.calendivity.presentation.ui.fragment.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardFragment, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardFragment, CalendarFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardFragment, GroupFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.item_4 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.dashboardFragment, SettingFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> {

                    false
                }
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.dashboardFragment, HomeFragment())
            .addToBackStack(null)
            .commit()

        setContentView(binding.root)

    }
}