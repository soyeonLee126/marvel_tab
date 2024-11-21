package com.example.marvel_tab.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id"], tableName = "character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("thumbnail_url") val thumbnailUrl: String?,
)
