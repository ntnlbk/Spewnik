package com.LibBib.spevn.domain.remoteDB

import javax.inject.Inject

class DownloadSongUseCase @Inject constructor(private val repository: RemoteDatabaseRepository) {
    operator fun invoke(songName: String) =
        repository.downloadSong(songName)
}