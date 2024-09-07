package com.LibBib.spewnik.presentation

import android.app.Application
import com.LibBib.spewnik.data.SongRoomDatabase
import com.LibBib.spewnik.di.DaggerApplicationComponent

class SpewnikApplication: Application() {
    val database by lazy { SongRoomDatabase.getDatabase(this) }

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}