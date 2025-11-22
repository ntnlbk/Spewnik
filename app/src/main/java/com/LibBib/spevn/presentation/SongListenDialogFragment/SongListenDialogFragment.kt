package com.LibBib.spevn.presentation.SongListenDialogFragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.LibBib.spevn.R
import com.LibBib.spevn.databinding.SongListenDialogBinding
import com.LibBib.spevn.presentation.SongFragment.PlayerUIState
import com.LibBib.spevn.presentation.SongFragment.SongViewModel
import kotlinx.coroutines.launch

class SongListenDialogFragment : DialogFragment() {

    private val viewModel: SongViewModel by lazy {
        ViewModelProvider(requireParentFragment())[SongViewModel::class.java]
    }


    var callback: ListenDialogCallback? = null
    private var _binding: SongListenDialogBinding? = null
    private val binding: SongListenDialogBinding
        get() = _binding ?: throw Exception("SongListenDialogBinding is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = SongListenDialogBinding.inflate(inflater)
        setupOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playerUIState.collect { it ->
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(state: PlayerUIState) {
        if (state.isPlaying) {
            binding.playBtn.setImageResource(R.drawable.pause_icon)
            binding.playBtn.setOnClickListener {
                callback?.onPauseClicked()
            }
        } else {
            binding.playBtn.setImageResource(R.drawable.play_button_icon)
            binding.playBtn.setOnClickListener {
                callback?.onPlayClicked()
            }
        }
        binding.songNameDialogTv.text = getString(R.string.thanks_to_spevy)
        val posSec = (state.currentPosition / 1000).toInt()
        val durSec = (state.duration / 1000).toInt()
        binding.songTimeTv.text = formatTime(durSec)
        binding.actualTimeTv.text = formatTime(posSec)

        binding.songSeekbar.max = state.duration.toInt()
        binding.songSeekbar.progress = state.currentPosition.toInt()

        binding.songSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                p0: SeekBar?,
                p1: Int,
                p2: Boolean,
            ) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                callback?.onSeekTo(binding.songSeekbar.progress.toLong())
            }

        })

    }

    private fun setupOnClickListeners() {
        binding.closeDialogBtn.setOnClickListener {
            dialog?.dismiss()
        }
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun formatTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%02d:%02d".format(m, s)
    }

    companion object {
        fun newInstance(): SongListenDialogFragment {
            return SongListenDialogFragment()
        }

    }
}

interface ListenDialogCallback {
    fun onPlayClicked()
    fun onPauseClicked()
    fun onSeekTo(position: Long)
}