package com.LibBib.spewnik.presentation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        // Inflate the layout for this fragment
        _binding = FragmentSongBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, args.songArg.text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}