package com.example.spewnik.di

import android.app.Application
import com.example.spewnik.data.SongListDao
import com.example.spewnik.data.SongListRepositoryImpl
import com.example.spewnik.data.SongRoomDatabase
import com.example.spewnik.domain.SongListRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun bindSongListRepository(impl: SongListRepositoryImpl): SongListRepository {
        return impl
    }

    @Provides
    fun provideSongListDao(application: Application): SongListDao {
        return SongRoomDatabase.getDatabase(application).songListDao()
    }

}