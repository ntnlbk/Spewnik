package com.LibBib.spevn.domain

import javax.inject.Inject

class GetSongUseCase @Inject constructor(private val songListRepository: SongListRepository) {
    operator fun invoke(id: Int) =
        songListRepository.getSong(id)
}