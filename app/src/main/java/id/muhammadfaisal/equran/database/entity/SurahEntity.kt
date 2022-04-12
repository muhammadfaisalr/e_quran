package id.muhammadfaisal.equran.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "m_surah")
data class SurahEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "latin_name") val latinName: String,
    @ColumnInfo(name = "total_ayah") val totalAyah: String,
    @ColumnInfo(name = "dropped_of_at") val droppedOfAt: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "mean") val mean: String,
    @ColumnInfo(name = "audio") val audio: String
) : Serializable