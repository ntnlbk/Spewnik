package com.LibBib.spewnik.presentation

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.LibBib.spewnik.databinding.FragmentSongBinding
import com.LibBib.spewnik.di.SpewnikApplication
import kotlinx.coroutines.launch
import javax.inject.Inject


class SongFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: SongViewModel.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SongViewModel.factory(viewModelFactory, songId!!)
        )[SongViewModel::class.java]
    }
    private val component by lazy {
        (requireActivity().application as SpewnikApplication).component
    }

    private val args by navArgs<SongFragmentArgs>()

    private var _binding: FragmentSongBinding? = null
    private val binding: FragmentSongBinding
        get() = _binding ?: throw Exception("SongFragmentBinding is null")

    private var songId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        parseArgs()
        setupTextViews()
        observeViewModel()

        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    is SongFragmentState.Content -> {
                        binding.songNameTv.text = it.name
                        binding.songTextTv.text = it.text
                        binding.songProgressBar.visibility = View.INVISIBLE
                    }
                    is SongFragmentState.Progress -> {
                        binding.songProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupTextViews() {
        binding.songTextTv.movementMethod = ScrollingMovementMethod()
    }


    private fun parseArgs() {
        songId = args.songId
        if (songId == SONG_ID_DEFAULT_VALUE) {
            songId = requireArguments().getInt(SONG_ID_KEY_ARG)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val SONG_ID_DEFAULT_VALUE = -1
        private const val SONG_ID_KEY_ARG = "song arg"
        fun newInstance(songId: Int): SongFragment {
            return SongFragment().apply {
                arguments = Bundle().apply {
                    putInt(SONG_ID_KEY_ARG, songId)
                }
            }
        }
    }
}