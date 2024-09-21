package com.LibBib.spewnik.presentation.OptionsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.LibBib.spewnik.databinding.FragmentOptionsBinding
import com.LibBib.spewnik.di.SpewnikApplication
import com.LibBib.spewnik.di.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject


class OptionsFragment : Fragment() {

    private val component by lazy{
        (requireActivity().application as SpewnikApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[OptionsViewModel::class.java]
    }

    private var _binding: FragmentOptionsBinding? = null
    private val binding: FragmentOptionsBinding
        get() = _binding ?: throw Exception("FragmentOptionsBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
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
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    is OptionsFragmentState.Content -> {
                        updateViewsWithContent(it)
                    }
                }
            }
        }
    }

    private fun updateViewsWithContent(it: OptionsFragmentState.Content) {
        binding.chordsCb.isChecked = it.isChordsVisible
        binding.transposeNumberTv.text = it.transposeInt.toString()
        binding.textSizeNumberTv.text = it.textSize.toString()
        binding.darkModeCb.isChecked = it.isDarkMode
    }

    private fun setupOnClickListeners() {
        binding.backBtnOptionsFragment.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.darkModeIv.setOnClickListener{
            //binding.darkModeCb.isChecked = !binding.darkModeCb.isChecked
        }

        binding.showChordsIv.setOnClickListener {
            //binding.chordsCb.isChecked = !binding.chordsCb.isChecked
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