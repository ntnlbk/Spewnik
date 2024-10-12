package com.LibBib.spevn.domain.remoteDB

import kotlinx.coroutines.flow.SharedFlow

interface RemoteDatabaseRepository {
    fun getActualVersionUseCase(): SharedFlow<Int>
}