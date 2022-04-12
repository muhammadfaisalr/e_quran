package id.muhammadfaisal.equran.helper

import android.content.Context
import androidx.room.Room
import id.muhammadfaisal.equran.database.EQuranDatabase
import id.muhammadfaisal.equran.database.dao.AyahSavedDao
import id.muhammadfaisal.equran.database.dao.SurahDao

class DatabaseHelper {
    companion object {
        private fun connect(context: Context): EQuranDatabase {
            return Room.databaseBuilder(context, EQuranDatabase::class.java, "app-local-db")
                .allowMainThreadQueries()
                .build()
        }

        fun ayahSavedDao(context: Context): AyahSavedDao {
            val database = connect(context)

            return database.ayahSavedDao()
        }

        fun surahDao(context: Context): SurahDao {
            val database = connect(context)

            return database.surahDao()
        }
    }
}