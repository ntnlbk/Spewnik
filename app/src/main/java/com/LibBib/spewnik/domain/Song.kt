package com.LibBib.spewnik.domain

data class Song(
    val name: String,
    val text: String,
    val author: String,
    val types: Set<SongType>,
    val typesString: String,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}