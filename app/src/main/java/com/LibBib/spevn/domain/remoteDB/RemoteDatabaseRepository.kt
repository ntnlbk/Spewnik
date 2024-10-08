package com.LibBib.spevn.domain.remoteDB

import kotlinx.coroutines.flow.SharedFlow

interface RemoteDatabaseRepository {
    suspend fun getActualVersionUseCase(): SharedFlow<Int>
}