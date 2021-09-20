package com.example.mytestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytestapp.databinding.ActivityMainBinding
import com.example.mytestapp.question_one.TimerCalculation
import com.example.mytestapp.question_two.TimerTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTimerCalculation.setOnClickListener {
            startActivity(Intent(this, TimerCalculation::class.java))
        }


        binding.btnTimerTask.setOnClickListener {
            startActivity(Intent(this, TimerTask::class.java))
        }

    }
}