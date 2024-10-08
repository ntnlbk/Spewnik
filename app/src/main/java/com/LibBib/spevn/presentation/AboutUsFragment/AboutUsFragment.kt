package com.LibBib.spevn.presentation.AboutUsFragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.LibBib.spevn.databinding.FragmentAboutUsBinding
import com.LibBib.spevn.presentation.MainActivity.Companion.GOOGLE_PLAY_APP
import com.LibBib.spevn.presentation.MainActivity.Companion.GOOGLE_PLAY_APP_URL

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding: FragmentAboutUsBinding
        get() = _binding ?: throw Exception("FragmentAboutUsBinding is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.aboutUsBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.helpProjectBtn.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_APP_URL)))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_APP)))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAboutUsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}