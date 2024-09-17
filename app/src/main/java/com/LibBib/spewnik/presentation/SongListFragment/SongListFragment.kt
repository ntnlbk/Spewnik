package com.LibBib.spewnik.presentation.SongListFragment

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.LibBib.spewnik.R
import com.LibBib.spewnik.databinding.FragmentSongListBinding
import com.LibBib.spewnik.di.SpewnikApplication
import com.LibBib.spewnik.di.ViewModelFactory
import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.presentation.SongFragment.SongFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class SongListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: SongListAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SongListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as SpewnikApplication).component
    }

    private var _binding: FragmentSongListBinding? = null
    private val binding: FragmentSongListBinding
        get() = _binding ?: throw Exception("FragmentSongListBinding is null")

    private val typesBtn by lazy {
        listOf(
            binding.allBtn,
            binding.partsOfMessBtn,
            binding.longBtn,
            binding.shortBtn
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        setupRecyclerView()
        observeViewModel()
        setOnTextChangedListener()
        setOnClickListeners()
    }

    private fun setOnTextChangedListener() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchInList(binding.searchEt.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun setOnClickListeners() {
        for (i in typesBtn) {
            i.setOnClickListener {
                sortSongListByType(i)
            }
        }
    }

    private fun sortSongListByType(i: TextView) {
        viewModel.sortSongListByType(i.text.toString())
        i.setTextColor(ContextCompat.getColor(requireActivity(), R.color.C2))
        for (notClickedTv in typesBtn) {
            if (notClickedTv != i) {
                notClickedTv.setTextColor(ContextCompat.getColor(requireActivity(), R.color.C4))
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is SongListFragmentState.Content -> {
                        adapter.submitList(it.songList)
                        binding.listProgressBar.visibility = View.INVISIBLE
                    }

                    is SongListFragmentState.Progress -> {
                        binding.listProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter.onSongItemClickListener = {
            launchSongFragment(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun launchSongFragment(it: Song) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            launchInPortraitMode(it)
        } else {
            launchWithLandscapeMode(it)
        }
    }

    private fun launchWithLandscapeMode(it: Song) {
        val fragment = SongFragment.newInstance(it.id)
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerViewSongText, fragment)
            .addToBackStack(SONG_FRAGMENT_BACK_STACK_NAME)
            .commit()
    }

    private fun launchInPortraitMode(it: Song) {
        findNavController().navigate(
            SongListFragmentDirections.actionSongListFragmentToSongFragment(
                it.id
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val SONG_FRAGMENT_BACK_STACK_NAME = "song_fragment_backstack"
    }
}