package com.LibBib.spevn.di

import android.app.Application
import com.LibBib.spevn.presentation.MainActivity
import com.LibBib.spevn.presentation.OptionsFragment.OptionsFragment
import com.LibBib.spevn.presentation.SongFragment.SongFragment
import com.LibBib.spevn.presentation.SongListFragment.SongListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(songListFragment: SongListFragment)

    fun inject(songFragment: SongFragment)

    fun inject(optionsFragment: OptionsFragment)

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}