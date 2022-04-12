package id.muhammadfaisal.equran.api

import id.muhammadfaisal.equran.api.model.DetailSurahResponse
import id.muhammadfaisal.equran.api.model.ListSurahResponse
import id.muhammadfaisal.equran.api.model.ListSurahResponseItem
import id.muhammadfaisal.equran.utils.Constant
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET(Constant.URL.LIST_SURAH)
    fun listSurah(): Observable<List<ListSurahResponseItem>>

    @GET("surat/{surahNumber}")
    fun detailSurah(@Path("surahNumber") surahNumber: String): Observable<DetailSurahResponse>

    @GET("tafsir/{surahNumber}")
    fun tafsir(@Path("surahNumber") surahNumber: String): Observable<DetailSurahResponse>
}