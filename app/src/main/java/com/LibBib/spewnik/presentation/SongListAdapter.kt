package com.LibBib.spewnik.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.LibBib.spewnik.databinding.SongItemBinding
import com.LibBib.spewnik.domain.Song
import javax.inject.Inject

class SongListAdapter @Inject constructor() : ListAdapter<Song, SongListAdapter.SongViewHolder>(SongDiffCallback()) {

    class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.binding.tvName.text = song.name
        holder.binding.tvTypes.text = song.typesString
    }
}