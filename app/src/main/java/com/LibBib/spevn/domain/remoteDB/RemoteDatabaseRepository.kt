package com.LibBib.spevn.domain.remoteDB

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.io.File

interface RemoteDatabaseRepository {
    fun getActualVersionUseCase(): SharedFlow<Int>
    fun downloadSong(songName: String): Flow<Result<File>>
}