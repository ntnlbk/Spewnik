package com.LibBib.spewnik.domain

import javax.inject.Inject

class GetSongListUseCase @Inject constructor(private val songListRepository: SongListRepository) {
    operator fun invoke(songType: SongType) = songListRepository.getSongList(songType)
}