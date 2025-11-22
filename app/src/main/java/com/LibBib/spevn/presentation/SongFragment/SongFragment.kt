package com.LibBib.spevn.presentation.SongFragment


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.LibBib.spevn.R
import com.LibBib.spevn.databinding.FragmentSongBinding
import com.LibBib.spevn.di.SpewnikApplication
import com.LibBib.spevn.presentation.OptionsFragment.OptionsFragment
import com.LibBib.spevn.presentation.SongListenDialogFragment.ListenDialogCallback
import com.LibBib.spevn.presentation.SongListenDialogFragment.SongListenDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class SongFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: SongViewModel.Factory


    private val viewModel by lazy {
        ViewModelProvider(
            this, SongViewModel.factory(viewModelFactory, songId!!)
        )[SongViewModel::class.java]
    }
    private val component by lazy {
        (requireActivity().application as SpewnikApplication).component
    }

    private val args by navArgs<SongFragmentArgs>()

    private var MODE = UNKNOWN_MODE

    private var _binding: FragmentSongBinding? = null
    private val binding: FragmentSongBinding
        get() = _binding ?: throw Exception("SongFragmentBinding is null")

    private var songId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        setupClickListeners()

    }

    private fun setupClickListeners() {
        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.optionsBtn.setOnClickListener {
            when (MODE) {
                PORTRAIT_MODE -> {
                    launchOptionsFragmentInPortraitMode()
                }

                LANDSCAPE_MODE -> {
                    launchOptionsFragmentInLandscapeMode()
                }

                else -> {
                    throw Exception("unknown mode")
                }
            }
        }
        if (MODE == PORTRAIT_MODE)
            binding.listenBtn.setOnClickListener {
                viewModel.listenButtonClicked()
            }
    }

    private fun showListenDialog() {

        val dialog = SongListenDialogFragment
            .newInstance()
        dialog.callback = object : ListenDialogCallback {
            override fun onPlayClicked() {
                viewModel.playButtonClicked()
            }

            override fun onPauseClicked() {
                viewModel.pauseButtonClicked()
            }

            override fun onSeekTo(position: Long) {

            }

            override fun onDismissed() {
            }

        }
        dialog.show(childFragmentManager, SONG_LISTEN_DIALOG_TAG)


    }

    private fun launchOptionsFragmentInPortraitMode() {
        findNavController().navigate(
            SongFragmentDirections.actionSongFragmentToOptionsFragment()
        )
    }

    private fun launchOptionsFragmentInLandscapeMode() {
        val fragment = OptionsFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewSongText, fragment).addToBackStack("").commit()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is SongFragmentState.Content -> {
                            binding.songNameTv.text = it.name
                            binding.songTextTv.text = it.text
                            binding.songTextTv.setTextSize(
                                TypedValue.COMPLEX_UNIT_SP,
                                TEXT_VIEW_DEFAULT_TEXT_SIZE + it.textSizeFromOptions.toFloat()
                            )
                            binding.songProgressBar.visibility = View.INVISIBLE
                            binding.listenBtn.isClickable = true
                        }

                        is SongFragmentState.Progress -> {
                            binding.songProgressBar.visibility = View.VISIBLE
                            binding.listenBtn.isClickable = false
                        }


                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.songPlayerEvent.collect {
                    when (it) {
                        is SongPlayerEvent.SongFileDownloadError -> {
                            Toast.makeText(
                                requireActivity(),
                                it.message
                                    ?: requireActivity().resources.getString(R.string.unknown_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.songProgressBar.visibility = View.INVISIBLE
                            binding.listenBtn.isClickable = true
                        }

                        is SongPlayerEvent.SongFileDownloadSuccessful -> {
                            showListenDialog()
                            binding.songProgressBar.visibility = View.INVISIBLE
                            binding.listenBtn.isClickable = true
                        }
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
        MODE = PORTRAIT_MODE
        if (songId == SONG_ID_DEFAULT_VALUE) {
            MODE = LANDSCAPE_MODE
            songId = requireArguments().getInt(SONG_ID_KEY_ARG)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        viewModel.updateScreen()
        super.onResume()
    }

    companion object {
        const val SONG_FRAGMENT_BACK_STACK_NAME = "song_fragment_backstack"
        private const val TEXT_VIEW_DEFAULT_TEXT_SIZE = 20
        private const val SONG_ID_DEFAULT_VALUE = -1
        private const val SONG_ID_KEY_ARG = "song arg"
        fun newInstance(songId: Int): SongFragment {
            return SongFragment().apply {
                arguments = Bundle().apply {
                    putInt(SONG_ID_KEY_ARG, songId)
                }
            }
        }

        private const val LANDSCAPE_MODE = 100
        private const val PORTRAIT_MODE = 111
        private const val UNKNOWN_MODE = 0
        private const val SONG_LISTEN_DIALOG_TAG = "song listen dialog"
    }
}