package com.LibBib.spewnik.di

import android.app.Application
import com.LibBib.spewnik.presentation.SongFragment
import com.LibBib.spewnik.presentation.SongListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(songListFragment: SongListFragment)

    fun inject(songFragment: SongFragment)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}