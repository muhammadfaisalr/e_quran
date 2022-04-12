package id.muhammadfaisal.equran.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "m_ayah_saved")
data class AyahSavedEntity (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "surah_number") val surahNumber: String,
    @ColumnInfo(name = "ayah_number") val ayahNumber: String,
    @ColumnInfo(name = "arabic") val arabic: String,
    @ColumnInfo(name = "latin") val latin: String,
    @ColumnInfo(name = "indonesian") val indonesian: String,
    @ColumnInfo(name = "position") val position: Int,
) : Serializable