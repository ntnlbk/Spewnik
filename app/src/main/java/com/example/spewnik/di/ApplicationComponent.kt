package com.example.spewnik.di

import android.app.Application
import com.example.spewnik.presentation.SongListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(songListFragment: SongListFragment)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}