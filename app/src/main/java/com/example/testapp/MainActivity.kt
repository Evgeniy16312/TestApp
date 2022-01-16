package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.hw2.HomeWork2
import com.example.testapp.hw3.HomeWork3
import com.example.testapp.hw4.HomeWork4
import com.example.testapp.hw5.HomeWork5

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hw2.setOnClickListener { startActivity(Intent(this, HomeWork2::class.java)) }
        binding.hw3.setOnClickListener { startActivity(Intent(this, HomeWork3::class.java)) }
        binding.hw4.setOnClickListener { startActivity(Intent(this, HomeWork4::class.java)) }
        binding.hw5.setOnClickListener { startActivity(Intent(this, HomeWork5::class.java)) }

    }
}