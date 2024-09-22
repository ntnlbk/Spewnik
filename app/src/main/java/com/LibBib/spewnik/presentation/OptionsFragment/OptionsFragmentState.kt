package com.LibBib.spewnik.presentation.OptionsFragment

import com.LibBib.spewnik.domain.options.Options

sealed class OptionsFragmentState {
    data object Progress : OptionsFragmentState()
    class Content(
        val options: Options,
    ) : OptionsFragmentState()
}
