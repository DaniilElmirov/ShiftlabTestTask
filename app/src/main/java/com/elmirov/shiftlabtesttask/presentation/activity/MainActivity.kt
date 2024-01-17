package com.elmirov.shiftlabtesttask.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elmirov.shiftlabtesttask.R
import com.elmirov.shiftlabtesttask.ShiftlabApplication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ShiftlabApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}