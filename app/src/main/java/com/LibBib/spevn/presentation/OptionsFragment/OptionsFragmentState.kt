package com.LibBib.spevn.presentation.OptionsFragment

import com.LibBib.spevn.domain.options.Options

sealed class OptionsFragmentState {
    data object Progress : OptionsFragmentState()
    class Content(
        val options: Options,
    ) : OptionsFragmentState()
}
