package com.example.spewnik.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spewnik.databinding.TextRowItemBinding
import com.example.spewnik.domain.Song
import javax.inject.Inject

class SongListAdapter @Inject constructor() : ListAdapter<Song, SongListAdapter.SongViewHolder>(SongDiffCallback()) {

    class SongViewHolder(val binding: TextRowItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = TextRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.binding.tvName.text = getItem(position).name
    }
}