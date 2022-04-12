package id.muhammadfaisal.equran.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailSurahResponse(

	@field:SerializedName("nama")
	val name: String,

	@field:SerializedName("ayat")
	val ayah: List<AyatItem>,

	@field:SerializedName("tafsir")
	val tafsir: List<TafsirItem>,

	@field:SerializedName("nama_latin")
	val latinName: String,

	@field:SerializedName("jumlah_ayat")
	val totalAyah: String,

	@field:SerializedName("tempat_turun")
	val dropAt: String,

	@field:SerializedName("arti")
	val mean: String,

	@field:SerializedName("deskripsi")
	val description: String,

	@field:SerializedName("audio")
	val audio: String,

	@field:SerializedName("nomor")
	val number: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class TafsirItem(

	@field:SerializedName("ayat")
	val ayat: String? = null,

	@field:SerializedName("tafsir")
	val tafsir: String? = null
)


data class AyatItem(

	@field:SerializedName("ar")
	val arabic: String,

	@field:SerializedName("idn")
	val indonesian: String,

	@field:SerializedName("nomor")
	val number: String,

	@field:SerializedName("tr")
	val translate: String
) : Serializable
