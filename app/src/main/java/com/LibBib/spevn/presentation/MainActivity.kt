package com.LibBib.spevn.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
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
    private lateinit var sharedPreferences: SharedPreferences

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
        sharedPreferences = getSharedPreferences(prefsName, MODE_PRIVATE)
        checkAndShowWhatsNewDialog()
        lifecycleScope.launch {
            checkUpdate()
        }
    }

    private fun checkAndShowWhatsNewDialog() {

        val lastShownVersionCode = sharedPreferences.getInt(lastVersionKey, 0)

        if (BUILD_ACTUAL_VERSION > lastShownVersionCode) {
            // Show the "What's New" dialog
            WhatsNewDialogFragment().show(supportFragmentManager, "WhatsNewDialog")

            // Update the last shown version in SharedPreferences
            sharedPreferences.edit { putInt(lastVersionKey, BUILD_ACTUAL_VERSION) }
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
                GOOGLE_PLAY_APP.toUri()
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

    companion object {
        class WhatsNewDialogFragment : DialogFragment() {

            override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
                return activity?.let {
                    val builder = AlertDialog.Builder(it)
                    val inflater = requireActivity().layoutInflater
                    val view = inflater.inflate(R.layout.dialog_whats_new, null)
                    builder.setView(view)
                    view.findViewById<TextView>(R.id.exit_tv).setOnClickListener {
                        dialog?.dismiss()
                    }
                    val spanStringGoToTelegram =
                        SpannableString(getString(R.string.whats_new_second_block))
                    spanStringGoToTelegram.setSpan(
                        UnderlineSpan(),
                        0,
                        getString(R.string.whats_new_second_block).length,
                        0
                    )
                    with (view.findViewById<TextView>(R.id.go_to_telegram_tv)){
                        text = spanStringGoToTelegram
                        setOnClickListener {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                TELEGRAM_URL.toUri()
                            )
                            startActivity(intent)
                        }
                }
                    builder.create()
                } ?: throw IllegalStateException("Activity cannot be null")
            }
        }

        private const val BUILD_ACTUAL_VERSION = 16
        const val TELEGRAM_URL = "https://t.me/spewnikchat_bot"
        const val GOOGLE_PLAY_APP = "https://play.google.com/store/apps/details?id=com.LibBib.spevn"
        const val GOOGLE_PLAY_APP_URL = "market://details?id=com.LibBib.spevn"
        private const val prefsName = "AppPrefs"
        private const val lastVersionKey = "last_version_code"
    }
}