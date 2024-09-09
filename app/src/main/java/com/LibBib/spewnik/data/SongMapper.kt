package com.LibBib.spewnik.data

import com.LibBib.spewnik.domain.Song
import com.LibBib.spewnik.domain.SongType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SongMapper @Inject constructor() {

    fun mapDbModelToEntity(dbModel: SongDbModel): Song {

        return Song(
            dbModel.name,
            dbModel.text,
            dbModel.author,
            mapStringTypesToSetTypes(dbModel.types),
            dbModel.types,
            dbModel.id
        )
    }

    fun mapListDbModelToListEntity(list: Flow<List<SongDbModel>>): Flow<List<Song>> {
        return list.map { it.map { dbModel -> mapDbModelToEntity(dbModel) } }
    }

    private fun mapStringTypesToSetTypes(stringTypes: String): Set<SongType> {
        val setTypes = mutableSetOf<SongType>()
        val stringTypesLowerCase = stringTypes.lowercase()
        if (stringTypesLowerCase.contains(SHORT_TYPE_STRING))
            setTypes.add(SongType.SHORT)
        if (stringTypesLowerCase.contains(LONG_TYPE_STRING))
            setTypes.add(SongType.LONG)
        if (stringTypesLowerCase.contains(PART_OF_MASS_TYPE_STRING))
            setTypes.add(SongType.PART_OF_MASS)
        return setTypes
    }

    companion object {
        private const val SHORT_TYPE_STRING = "кароткія"
        private const val LONG_TYPE_STRING = "доўгія"
        private const val PART_OF_MASS_TYPE_STRING = "часткі імшы"
    }

}
