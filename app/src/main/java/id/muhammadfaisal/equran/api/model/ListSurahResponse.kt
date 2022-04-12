package id.muhammadfaisal.equran.api.model

import com.google.gson.annotations.SerializedName

data class ListSurahResponse(
	val listSurahResponse: List<ListSurahResponseItem>? = null
)

data class ListSurahResponseItem(
	@SerializedName("nomor") val number: String = "",
	@SerializedName("nama") val name: String = "",
	@SerializedName("nama_latin") val latinName: String = "",
	@SerializedName("jumlah_ayat") val totalAyah: String = "",
	@SerializedName("tempat_turun") val droppedAt: String = "",
	@SerializedName("arti") val mean: String = "",
	@SerializedName("deskripsi") val description: String = "",
	@SerializedName("audio") val audio: String = ""
)

