package com.LibBib.spewnik.domain.options

import javax.inject.Inject


class SaveOptionsUseCase @Inject constructor(private val repository: OptionsRepository) {
    suspend operator fun invoke(options: Options) = repository.saveOptions(options)
}