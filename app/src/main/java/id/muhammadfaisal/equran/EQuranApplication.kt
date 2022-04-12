package id.muhammadfaisal.equran

import android.app.Application
import android.util.Log
import id.muhammadfaisal.equran.api.model.ListSurahResponse
import id.muhammadfaisal.equran.api.model.ListSurahResponseItem
import id.muhammadfaisal.equran.database.entity.SurahEntity
import id.muhammadfaisal.equran.helper.ApiHelper
import id.muhammadfaisal.equran.helper.DatabaseHelper
import id.muhammadfaisal.equran.utils.Constant
import id.muhammadfaisal.equran.utils.SharedPrefs
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

class EQuranApplication : Application() {
    val TAG = EQuranApplication::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")

        var isAlreadyOpen = SharedPrefs.get(this, Constant.Key.IS_ALREADY_OPEN) as Boolean?

        if (isAlreadyOpen == null) {
            isAlreadyOpen = false
        }

        if (!isAlreadyOpen) {
            this.getSurahList()
        }
    }

    private fun getSurahList() {
        Log.i(TAG, "start getSurahList()")

        var listSurahResponses : List<ListSurahResponseItem> = ArrayList()
        CompositeDisposable().add(
            ApiHelper.surahList().subscribeWith(object : DisposableObserver<List<ListSurahResponseItem>>() {
                override fun onNext(t: List<ListSurahResponseItem>) {
                    Log.d(TAG, "onNext()")
                    listSurahResponses = t
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError()")
                    e.printStackTrace()
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete()")
                    SharedPrefs.save(this@EQuranApplication, Constant.Key.IS_ALREADY_OPEN, true)

                    val surahDao = DatabaseHelper.surahDao(this@EQuranApplication)

                    Log.d(TAG, "Try saving to local db with length : ${listSurahResponses.size}")
                    for (surah in listSurahResponses) {
                        val surahEntity = SurahEntity(surah.number.toInt(), surah.name, surah.latinName, surah.totalAyah, surah.droppedAt, surah.description, surah.mean, surah.audio)
                        surahDao.insert(surahEntity)
                    }
                }
            })
        )
    }
}