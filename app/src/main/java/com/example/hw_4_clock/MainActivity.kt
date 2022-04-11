package com.example.hw_4_clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw_4_clock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(CustomView(this))
    }
}