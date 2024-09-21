package com.LibBib.spewnik.presentation.OptionsFragment

import androidx.lifecycle.ViewModel
import com.LibBib.spewnik.domain.options.GetOptionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
        private val getOptionsUseCase: GetOptionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<OptionsFragmentState>(OptionsFragmentState.Content())
    val state = _state.asStateFlow()

    init {
        getOptionsUseCase()
    }
}