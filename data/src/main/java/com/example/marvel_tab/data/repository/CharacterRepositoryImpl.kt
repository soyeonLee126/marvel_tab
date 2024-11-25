package com.example.marvel_tab.data.repository

import android.util.Log
import com.example.marvel_tab.core.model.Character
import com.example.marvel_tab.core.util.EncryptionManager.md5
import com.example.marvel_tab.data.BuildConfig
import com.example.marvel_tab.data.local.CharacterDao
import com.example.marvel_tab.data.remote.api.CharacterService
import com.example.marvel_tab.data.repository.CharacterMapper.toDomain
import com.example.marvel_tab.data.repository.CharacterMapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.sql.Timestamp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterDao: CharacterDao
) : CharacterRepository {
    private var internalOffset = 0

    override fun getCharacters(name: String, offset: Int?): Flow<List<Character>> = flow {
        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        val hash = md5("$ts${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}")
        val query = name.ifEmpty { null }

        if (offset != null) internalOffset = offset

       try {
           emit(
               characterService.getCharacters(
                   name = query,
                   hash = hash,
                   ts = ts,
                   offset = internalOffset + 1
               )
                   .getOrThrow().data.toDomain().also {
                       internalOffset += it.size
                   }
           )
       } catch (e: Exception) {
           Log.e("CharacterRepository", "Error fetching characters", e)
       }
    }

    override suspend fun saveCharacter(character: Character): Unit {
        return characterDao.saveCharacter(character.toEntity())
    }

    override suspend fun deleteCharacter(character: Character) =
        characterDao.deleteCharacter(character.id)

    override fun getFavoriteCharacters(): Flow<List<Character>> =
        characterDao.getFavoriteCharacters().toDomain()
}