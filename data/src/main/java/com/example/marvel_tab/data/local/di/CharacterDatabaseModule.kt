package com.example.marvel_tab.data.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.marvel_tab.data.local.CharacterDao
import com.example.marvel_tab.data.local.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDatabaseModule {

    const val MAX_LIMIT = 5

    private val DATABASE_CALLBACK = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL("""
            CREATE TRIGGER IF NOT EXISTS limit_log_table_trigger
            AFTER INSERT ON character
            WHEN (SELECT COUNT(*) FROM character) > $MAX_LIMIT
            BEGIN
                DELETE FROM character 
                WHERE id IN (
                    SELECT id FROM character 
                    ORDER BY id ASC LIMIT (SELECT COUNT(*) - $MAX_LIMIT FROM character)
                );
            END;
        """.trimIndent())
        }
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MarvelDatabase {
        return Room.databaseBuilder(
            appContext,
            MarvelDatabase::class.java,
            "marvel.db"
        )
            .addCallback(DATABASE_CALLBACK)
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: MarvelDatabase): CharacterDao {
        return database.characterDao()
    }
}