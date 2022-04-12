package id.muhammadfaisal.equran.database.dao

import androidx.room.*
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity

@Dao
interface AyahSavedDao {

    @Insert
    fun insert(ayahSavedEntity: AyahSavedEntity)

    @Update
    fun update(ayahSavedEntity: AyahSavedEntity)

    @Delete
    fun delete(ayahSavedEntity: AyahSavedEntity)

    @Query("SELECT * FROM m_ayah_saved")
    fun getAll() : List<AyahSavedEntity>

    @Query("SELECT * FROM m_ayah_saved WHERE surah_number = :surahNumber")
    fun getAllBySurahNumber(surahNumber: String) : List<AyahSavedEntity>

    @Query("SELECT * FROM m_ayah_saved WHERE surah_number = :surahNumber AND ayah_number = :ayahNumber")
    fun getAllBySurahAndAyahNumber(surahNumber: String, ayahNumber: String) : List<AyahSavedEntity>
}