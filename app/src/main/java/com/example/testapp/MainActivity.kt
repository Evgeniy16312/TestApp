package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hw2.setOnClickListener { startActivity(Intent(this, HomeWork2::class.java)) }
        binding.hw3.setOnClickListener { startActivity(Intent(this, HomeWork3::class.java)) }
        binding.hw4.setOnClickListener { startActivity(Intent(this, HomeWork4::class.java)) }

    }
}