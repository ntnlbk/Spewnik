package com.LibBib.spewnik.di

import android.app.Application
import com.LibBib.spewnik.data.SongListDao
import com.LibBib.spewnik.data.SongListRepositoryImpl
import com.LibBib.spewnik.data.SongRoomDatabase
import com.LibBib.spewnik.data.options.OptionsRepositoryImpl
import com.LibBib.spewnik.domain.SongListRepository
import com.LibBib.spewnik.domain.options.OptionsRepository
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