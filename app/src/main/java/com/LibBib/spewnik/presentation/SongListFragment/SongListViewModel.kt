package com.LibBib.spewnik.presentation.SongListFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LibBib.spewnik.domain.GetSongListUseCase
import com.LibBib.spewnik.domain.SongType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    getSongListUseCase: GetSongListUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<SongListFragmentState>(SongListFragmentState.Progress)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getSongListUseCase(SongType.ALL).collect{
                _state.value = SongListFragmentState.Content(it)
            }
        }

    }

}

