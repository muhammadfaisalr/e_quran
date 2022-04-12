package id.muhammadfaisal.equran.database.dao

import androidx.room.*
import id.muhammadfaisal.equran.database.entity.SurahEntity

@Dao
interface SurahDao {
    @Insert
    fun insert(surahEntity: SurahEntity)

    @Update
    fun update(surahEntity: SurahEntity)

    @Delete
    fun delete(surahEntity: SurahEntity)

    @Query("SELECT * FROM m_surah")
    fun getAll() : List<SurahEntity>

    @Query("SELECT * FROM m_surah WHERE id = :id")
    fun get(id: Int) : SurahEntity

    @Query("SELECT * FROM m_surah WHERE latin_name LIKE '%' || :query || '%'")
    fun query(query: String) : List<SurahEntity>
}