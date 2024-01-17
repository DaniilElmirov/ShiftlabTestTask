package com.elmirov.shiftlabtesttask.di.component

import android.content.Context
import com.elmirov.shiftlabtesttask.MainActivity
import com.elmirov.shiftlabtesttask.ShiftlabApplication
import com.elmirov.shiftlabtesttask.di.annotation.ApplicationScope
import com.elmirov.shiftlabtesttask.di.module.DataModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: ShiftlabApplication)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): ApplicationComponent
    }
}