package com.example.mytestapp.question_two

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mytestapp.R
import com.example.mytestapp.databinding.ActivityTimerTaskBinding

class TimerTask : AppCompatActivity() {
    private lateinit var binding: ActivityTimerTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        setupInitialFragment()

        binding.bottomNav.setOnItemSelectedListener {
            val selectedFragment: Fragment
            when (it.itemId) {
                R.id.menu_timer -> {
                    selectedFragment = TimerFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, selectedFragment).commit()

                    true
                }
                R.id.menu_dashboard -> {
                    selectedFragment = DashboardFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameContainer, selectedFragment).commit()
                    true
                }
                else -> false
            }
        }


    }

    private fun setupInitialFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, TimerFragment()).commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}