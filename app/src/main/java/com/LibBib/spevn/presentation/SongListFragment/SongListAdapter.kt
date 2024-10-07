package com.LibBib.spevn.presentation.SongListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.LibBib.spevn.domain.Song
import com.LibBib.spevn.databinding.SongItemBinding
import javax.inject.Inject
import kotlin.random.Random

class SongListAdapter @Inject constructor() : ListAdapter<Song, SongListAdapter.SongViewHolder>(
    SongDiffCallback()
) {

    var onSongItemClickListener: ((Song) -> Unit)? = null

    class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.binding.tvName.text = song.name
        holder.binding.tvTypes.text = song.typesString
        holder.binding.root.setOnClickListener {
            onSongItemClickListener?.invoke(song)
        }
    }
    fun launchRandomSong(){
        onSongItemClickListener?.let { it(getItem(Random.nextInt(0, currentList.size))) }
    }
}