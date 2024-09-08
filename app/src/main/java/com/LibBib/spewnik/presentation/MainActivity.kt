package com.LibBib.spewnik.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.LibBib.spewnik.databinding.ActivityMainBinding
import com.LibBib.spewnik.presentation.SongListFragment.Companion.SONG_FRAGMENT_BACK_STACK_NAME

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.popBackStack(
            SONG_FRAGMENT_BACK_STACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}