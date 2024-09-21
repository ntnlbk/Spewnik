package com.LibBib.spewnik.presentation.OptionsFragment

sealed class OptionsFragmentState {
    class Content(
        val isChordsVisible: Boolean = true,
        val transposeInt: Int = 0,
        val chordsColor: Int = 0,
        val textSize: Int = 0,
        val isDarkMode: Boolean = false,
    ) : OptionsFragmentState()
}
