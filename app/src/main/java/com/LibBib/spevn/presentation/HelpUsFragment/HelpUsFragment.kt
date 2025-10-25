package com.LibBib.spevn.presentation.HelpUsFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.LibBib.spevn.databinding.FragmentHelpUsBinding

class HelpUsFragment : Fragment() {

    private var _binding: FragmentHelpUsBinding? = null
    private val binding: FragmentHelpUsBinding
        get() = _binding ?: throw Exception("FragmentHelpUsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHelpUsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpOnClickListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpOnClickListeners() {
        binding.goHelpProjectBtn.setOnClickListener {
            openUrl(GOOGLE_FORM_URL.toUri())
        }

        binding.helpUsBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openUrl(url: Uri) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            url
        )
        startActivity(intent)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val GOOGLE_FORM_URL = "https://forms.gle/wfznSVYAMJJu6jFd7"
    }
}