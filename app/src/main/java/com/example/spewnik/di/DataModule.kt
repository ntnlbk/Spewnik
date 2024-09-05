package com.example.spewnik.di

import com.example.spewnik.data.SongListRepositoryImpl
import com.example.spewnik.domain.SongListRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindSongListRepository(impl: SongListRepositoryImpl): SongListRepository

}