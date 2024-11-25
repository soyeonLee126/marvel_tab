package com.example.marvel_tab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvel_tab.data.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter(character: CharacterEntity)

    @Query("DELETE FROM character WHERE character_id is :characterId")
    suspend fun deleteCharacter(characterId: Int)

    @Query("SELECT * FROM character ORDER BY id ASC")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>
}