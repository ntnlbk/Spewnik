package com.LibBib.spewnik.di

import android.app.Application
import com.LibBib.spewnik.presentation.OptionsFragment.OptionsFragment
import com.LibBib.spewnik.presentation.SongFragment.SongFragment
import com.LibBib.spewnik.presentation.SongListFragment.SongListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(songListFragment: SongListFragment)

    fun inject(songFragment: SongFragment)

    fun inject(optionsFragment: OptionsFragment)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}