package com.example.testapp.hw5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp.databinding.ActivityHomeWork5Binding

class HomeWork5 : AppCompatActivity() {

    private lateinit var binding: ActivityHomeWork5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeWork5Binding.inflate(layoutInflater).also { setContentView(it.root) }

    }
}