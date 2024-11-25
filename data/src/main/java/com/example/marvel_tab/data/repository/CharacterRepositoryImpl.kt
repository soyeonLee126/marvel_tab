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
    private var internalQuery: String? = null

    override fun getCharacters(name: String): Flow<List<Character>> = flow {
        val (ts, hash) = generateApiParams()
        val query = name.ifEmpty { null }

        try {
            val response = characterService.getCharacters(
                name = query,
                hash = hash,
                ts = ts,
                offset = 0
            ).getOrThrow()

            val characters = response.data.toDomain()

            internalOffset = characters.size
            internalQuery = query

            emit(characters)
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching characters", e)
        }
    }

    override fun getMoreCharacters(): Flow<List<Character>> = flow {
        try {
            val (ts, hash) = generateApiParams()


            val response = characterService.getCharacters(
                name = internalQuery,
                hash = hash,
                ts = ts,
                offset = internalOffset + 1
            ).getOrThrow()

            val characters = response.data.toDomain()
            internalOffset += characters.size
            emit(characters)
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

    private fun generateApiParams(): Pair<String, String> {
        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        val hash = md5("$ts${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}")
        return Pair(ts, hash)
    }
}