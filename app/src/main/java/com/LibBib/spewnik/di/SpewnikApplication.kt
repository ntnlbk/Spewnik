package com.LibBib.spewnik.di

import android.app.Application
import com.LibBib.spewnik.data.SongRoomDatabase

class SpewnikApplication: Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}