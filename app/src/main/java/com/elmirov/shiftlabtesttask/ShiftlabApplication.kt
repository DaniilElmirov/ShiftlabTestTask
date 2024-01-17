package com.elmirov.shiftlabtesttask

import android.app.Application
import com.elmirov.shiftlabtesttask.di.component.DaggerApplicationComponent

class ShiftlabApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}