package com.LibBib.spewnik.di

import androidx.lifecycle.ViewModel
import com.LibBib.spewnik.presentation.OptionsFragment.OptionsViewModel
import com.LibBib.spewnik.presentation.SongListFragment.SongListViewModel
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

}