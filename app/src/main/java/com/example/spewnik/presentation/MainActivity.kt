package com.example.spewnik.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.spewnik.databinding.ActivityMainBinding
import com.example.spewnik.domain.SongListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: SongListRepository

    private val component by lazy {
        (application as SpewnikApplication).component
    }
    private val db by lazy {
        (application as SpewnikApplication).database
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        runBlocking {
            withContext(Dispatchers.IO){
                Log.d("MainActivity", repository.getSongList().toString())
            }

        }


    }


    private suspend fun resetDb() = withContext(Dispatchers.IO){
        db.runInTransaction{
            runBlocking {
                db.clearAllTables()
            }
        }
    }
}