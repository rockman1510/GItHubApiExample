package com.example.githubapiexample.di

import android.app.Application
import android.content.Context
import com.example.githubapiexample.commits.view.activity.CommitsActivity
import com.example.githubapiexample.commits.view.activity.CommitsByAuthorActivity
import com.example.githubapiexample.di.module.RepositoryModule
import com.example.githubapiexample.di.module.ServiceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ServiceModule::class, RepositoryModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(application: Application)
    fun inject(activity: CommitsActivity)
    fun inject(activity: CommitsByAuthorActivity)
}