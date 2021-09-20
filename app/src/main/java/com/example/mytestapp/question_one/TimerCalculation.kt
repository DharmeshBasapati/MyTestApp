package com.example.mytestapp.question_one

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytestapp.databinding.ActivityTimerCalculationBinding

class TimerCalculation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTimerCalculationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}