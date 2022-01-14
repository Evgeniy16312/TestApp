package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp.databinding.ActivityHomeWork4Binding
import com.example.testapp.databinding.ActivityMainBinding

class HomeWork4 : AppCompatActivity() {

    private lateinit var binding: ActivityHomeWork4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeWork4Binding.inflate(layoutInflater).also { setContentView(it.root) }

    }
}