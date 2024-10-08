package com.LibBib.spevn.domain.remoteDB

import javax.inject.Inject

class GetActualVersionUseCase @Inject constructor(private val repository: RemoteDatabaseRepository) {
    suspend operator fun invoke() =
        repository.getActualVersionUseCase()

}