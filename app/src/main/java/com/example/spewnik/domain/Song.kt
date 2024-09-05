package com.example.spewnik.domain

data class Song(
    val name: String,
    val text: String,
    val author: String,
    val types: Set<SongType>,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}