package com.test.gitsubs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.test.gitsubs.R

class MainActivity : AppCompatActivity() {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
    }

    private fun initNavigation() {
        nav = Navigation.findNavController(this, R.id.fragment_nav)
    }
}
