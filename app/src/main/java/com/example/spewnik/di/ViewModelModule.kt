package com.example.spewnik.di

import androidx.lifecycle.ViewModel
import com.example.spewnik.presentation.SongListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(SongListViewModel::class)
    @Binds
    fun bindSongListViewModel(impl: SongListViewModel): ViewModel
}