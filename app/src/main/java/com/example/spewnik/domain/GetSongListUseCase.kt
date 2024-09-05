package com.example.spewnik.domain

import javax.inject.Inject

class GetSongListUseCase @Inject constructor(private val songListRepository: SongListRepository) {
    operator fun invoke() = songListRepository.getSongList()
}