package com.LibBib.spewnik.presentation.OptionsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.LibBib.spewnik.R
import com.LibBib.spewnik.databinding.FragmentOptionsBinding


class OptionsFragment : Fragment() {


    private var _binding: FragmentOptionsBinding? = null
    private val binding: FragmentOptionsBinding
        get() = _binding ?: throw Exception("FragmentOptionsBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.backBtnOptionsFragment.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.darkModeIv.setOnClickListener{
            binding.darkModeCb.isChecked = !binding.darkModeCb.isChecked
        }

        binding.showChordsIv.setOnClickListener {
            binding.chordsCb.isChecked = !binding.chordsCb.isChecked
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() =
            OptionsFragment()

    }
}