package com.example.spewnik.presentation

import android.app.Application
import com.example.spewnik.data.SongRoomDatabase
import com.example.spewnik.di.DaggerApplicationComponent

class SpewnikApplication: Application() {
    val database by lazy { SongRoomDatabase.getDatabase(this) }

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}