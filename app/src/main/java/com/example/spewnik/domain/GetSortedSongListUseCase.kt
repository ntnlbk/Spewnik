package com.example.spewnik.domain

import javax.inject.Inject

class GetSortedSongListUseCase @Inject constructor(
    private val songListRepository: SongListRepository
) {
    operator fun invoke(songType: SongType) = songListRepository.getSortedSongList(songType)
}