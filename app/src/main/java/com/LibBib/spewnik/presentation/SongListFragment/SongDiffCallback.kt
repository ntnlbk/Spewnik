package com.LibBib.spewnik.presentation.SongListFragment

import androidx.recyclerview.widget.DiffUtil
import com.LibBib.spewnik.domain.Song

class SongDiffCallback: DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }

}