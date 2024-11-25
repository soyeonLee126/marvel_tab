package com.example.marvel_tab.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("character_id") val characterId: Int,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("thumbnail_url") val thumbnailUrl: String?,
)

