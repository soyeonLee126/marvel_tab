package com.example.marvel_tab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marvel_tab.data.model.CharacterEntity

@Database(
    entities = [
        CharacterEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}