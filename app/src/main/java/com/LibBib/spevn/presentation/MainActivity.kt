package com.LibBib.spevn.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.LibBib.spevn.R
import com.LibBib.spevn.databinding.ActivityMainBinding
import com.LibBib.spevn.di.SpewnikApplication
import com.LibBib.spevn.domain.remoteDB.GetActualVersionUseCase
import com.LibBib.spevn.presentation.SongFragment.SongFragment.Companion.SONG_FRAGMENT_BACK_STACK_NAME
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var getActualVersionUseCase: GetActualVersionUseCase

    private val component by lazy {
        (application as SpewnikApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clearActivity()
        lifecycleScope.launch {
            checkUpdate()
        }
    }



    private suspend fun checkUpdate() {
        getActualVersionUseCase.invoke().collect {
            if (it != BUILD_ACTUAL_VERSION) {
                showUpdateDialog()
            }
        }
    }

    private fun showUpdateDialog() {
        val title: String = getString(R.string.update_is_available)
        val message: String = getString(R.string.please_update)
        val button1String: String = getString(R.string.update)
        val button2String: String = getString(R.string.skip_update)
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            button1String
        ) { _, _ ->
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(GOOGLE_PLAY_APP)
            )
            startActivity(intent)
        }
        builder.setNegativeButton(
            button2String
        ) { _, _ -> }
        builder.setCancelable(false)
        builder.create()
        builder.show()
    }

    private fun clearActivity() {
        supportFragmentManager.popBackStack(
            SONG_FRAGMENT_BACK_STACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object{
        private const val BUILD_ACTUAL_VERSION = 14
        const val GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=com.LibBib.spevn"
        const val GOOGLE_PLAY_APP_URL = "market://details?id=com.LibBib.spevn"
    }
}