package com.LibBib.spewnik.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.LibBib.spewnik.databinding.FragmentSongListBinding
import com.LibBib.spewnik.di.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject


class SongListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: SongListAdapter

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[SongListViewModel::class.java]
    }

    private val component by lazy{
        (requireActivity().application as SpewnikApplication).component
    }

    private var _binding: FragmentSongListBinding? = null
    private val binding: FragmentSongListBinding
        get() = _binding ?: throw Exception("FragmentSongListBinding is null")

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
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.songList.collect {
                adapter.submitList(it)

            }
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}