package com.example.githubapiexample

import com.example.githubapiexample.di.AppComponent
import com.example.githubapiexample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initDaggerComponent()
    }

    open fun initDaggerComponent(): AppComponent {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        initDaggerComponent().inject(this)
        super.onCreate()
    }
}