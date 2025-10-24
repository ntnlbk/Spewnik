package com.LibBib.spevn.di

import androidx.lifecycle.ViewModel
import com.LibBib.spevn.presentation.HelpUsFragment.HelpUsViewModel
import com.LibBib.spevn.presentation.OptionsFragment.OptionsViewModel
import com.LibBib.spevn.presentation.SongListFragment.SongListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(SongListViewModel::class)
    @Binds
    fun bindSongListViewModel(impl: SongListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(OptionsViewModel::class)
    @Binds
    fun bindOptionsViewModel(impl: OptionsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HelpUsViewModel::class)
    @Binds
    fun bindHelpUsViewModel(impl: HelpUsViewModel): ViewModel

}