package com.LibBib.spevn.di

import android.app.Application
import com.LibBib.spevn.data.SongListDao
import com.LibBib.spevn.data.SongListRepositoryImpl
import com.LibBib.spevn.data.SongRoomDatabase
import com.LibBib.spevn.data.options.OptionsRepositoryImpl
import com.LibBib.spevn.domain.SongListRepository
import com.LibBib.spevn.domain.options.OptionsRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun bindSongListRepository(impl: SongListRepositoryImpl): SongListRepository {
        return impl
    }

    @Provides
    fun bindOptionsRepository(impl: OptionsRepositoryImpl): OptionsRepository {
        return impl
    }

    @Provides
    fun provideSongListDao(application: Application): SongListDao {
        return SongRoomDatabase.getDatabase(application).songListDao()
    }

}