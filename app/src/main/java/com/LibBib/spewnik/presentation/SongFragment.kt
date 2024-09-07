package com.LibBib.spewnik.presentation
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.LibBib.spewnik.databinding.FragmentSongBinding


class SongFragment : Fragment() {


    private val args by navArgs<SongFragmentArgs>()

    private var _binding: FragmentSongBinding? = null
    private val binding: FragmentSongBinding
        get() = _binding ?: throw Exception("SongFragmentBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextViews()

    }

    private fun setupTextViews() {
        val song = args.songArg
        binding.songNameTv.text = song.name
        binding.songTextTv.movementMethod = ScrollingMovementMethod()
        binding.songTextTv.text = song.text
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}