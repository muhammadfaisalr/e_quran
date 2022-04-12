package id.muhammadfaisal.equran.helper

import id.muhammadfaisal.equran.api.ApiServices
import id.muhammadfaisal.equran.api.model.DetailSurahResponse
import id.muhammadfaisal.equran.api.model.ListSurahResponse
import id.muhammadfaisal.equran.api.model.ListSurahResponseItem
import id.muhammadfaisal.equran.utils.RetrofitBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ApiHelper {
    companion object {
        private fun getServices() : ApiServices {
            return RetrofitBuilder.getRetrofit().create(ApiServices::class.java)
        }

        fun surahList() :Observable<List<ListSurahResponseItem>> {
            return getServices()
                .listSurah()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun detailSurah(surahNumber: String) :Observable<DetailSurahResponse> {
            return getServices()
                .detailSurah(surahNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }

        fun tafsir(surahNumber: String) :Observable<DetailSurahResponse> {
            return getServices()
                .tafsir(surahNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        }
    }
}