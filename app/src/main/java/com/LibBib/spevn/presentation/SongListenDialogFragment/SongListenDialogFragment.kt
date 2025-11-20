package com.LibBib.spevn.presentation.SongListenDialogFragment

import android.annotation.SuppressLint
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.LibBib.spevn.R
import java.util.concurrent.TimeUnit

class SongListenDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    private val songName by lazy {
        requireArguments().getString(SONG_NAME_ARGUMENT_KEY)
    }

    private val fileToPlayPath by lazy {
        requireArguments().getString(FILE_TO_PLAY_PATH_ARGUMENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.song_listen_dialog, container, false)
        view.findViewById<ImageView>(R.id.close_dialog_btn).setOnClickListener {
            dialog?.dismiss()
        }
        view.findViewById<TextView>(R.id.song_name_dialog_tv).text = songName
        view.findViewById<TextView>(R.id.song_time_tv).text = getAudioDuration(fileToPlayPath)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawableResource(android.R.color.transparent)
            val params = attributes
            val w = (resources.displayMetrics.widthPixels)
            params.width = w
            attributes = params
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @SuppressLint("DefaultLocale")
    private fun getAudioDuration(filePath: String?): String {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(filePath)

        val durationStr =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        val durationMs = durationStr?.toLongOrNull() ?: 0L

        retriever.release()

        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60

        return String.format("%02d:%02d", minutes, seconds)
    }

    companion object {
        fun newInstance(songName: String,
                        fileToPlayPath: String
        ): SongListenDialogFragment {
            return SongListenDialogFragment().apply {
                arguments = Bundle().apply{
                    putString(SONG_NAME_ARGUMENT_KEY, songName)
                    putString(FILE_TO_PLAY_PATH_ARGUMENT, fileToPlayPath)
                }
            }
        }
        private const val SONG_NAME_ARGUMENT_KEY = "song name"
        private const val FILE_TO_PLAY_PATH_ARGUMENT = "file to play path"
    }
}


//private fun play(file: File) {
//    val mediaItem = MediaItem.fromUri(Uri.fromFile(file))
//    player.setMediaItem(mediaItem)
//    player.prepare()
//    audioPlaying = true
//    player.play()
//}
//binding.buttonForTest.setOnClickListener {
//    if (player.isPlaying)
//        player.pause()
//    else {
//        if (audioPlaying)
//            player.play()
//        else
//            viewModel.listenButtonClicked()
//    }
//}