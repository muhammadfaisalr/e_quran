package id.muhammadfaisal.equran.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.muhammadfaisal.equran.database.dao.AyahSavedDao
import id.muhammadfaisal.equran.database.dao.SurahDao
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.database.entity.SurahEntity

@Database(entities = [AyahSavedEntity::class, SurahEntity::class], version = 1)
abstract class EQuranDatabase : RoomDatabase() {
    abstract fun ayahSavedDao() : AyahSavedDao
    abstract fun surahDao() : SurahDao
}