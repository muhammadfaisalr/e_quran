package id.muhammadfaisal.equran.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.adapter.DetailSurahAdapter
import id.muhammadfaisal.equran.adapter.TafsirAdapter
import id.muhammadfaisal.equran.api.model.DetailSurahResponse
import id.muhammadfaisal.equran.databinding.ActivityDetailSurahBinding
import id.muhammadfaisal.equran.helper.ApiHelper
import id.muhammadfaisal.equran.notification.CreateNotification
import id.muhammadfaisal.equran.notification.Track
import id.muhammadfaisal.equran.utils.Constant
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.io.IOException


class DetailSurahActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailSurahBinding
    private lateinit var notificationManager: NotificationManager
    private lateinit var mediaPlayer: MediaPlayer

    private val TAG = DetailSurahActivity::class.java.simpleName
    private var surahId: String = ""
    private var position: Int = 0

    //1 For Surah Mode, 2 For Tafsir Mode
    private var mode = 1
    private var isPlaying = false
    private var audio = ""
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailSurahBinding.inflate(this.layoutInflater)
        this.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        this.extract()
        this.init()

        this.surahMode()
        this.dataTafsir()
        this.dataSurah()

        this.createChannel()
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            CreateNotification.CHANNEL_ID,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_LOW
        )

        this.notificationManager = getSystemService(NotificationManager::class.java)
        this.notificationManager.createNotificationChannel(channel)
    }

    private fun dataTafsir() {
        var detailSurahResponse: DetailSurahResponse? = null
        CompositeDisposable().add(
            ApiHelper
                .tafsir(this.surahId)
                .subscribeWith(object : DisposableObserver<DetailSurahResponse>() {
                    override fun onNext(t: DetailSurahResponse) {
                        Log.d(TAG, "Tafsir: onNext()")
                        detailSurahResponse = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "Tafsir: onError()")
                        Toast.makeText(
                            this@DetailSurahActivity,
                            "Terjadi Kesalahan",
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        Log.d(TAG, "Tafsir: onComplete()")
                        this@DetailSurahActivity.binding.recyclerTafsir.adapter = TafsirAdapter(
                            this@DetailSurahActivity,
                            detailSurahResponse!!,
                            detailSurahResponse!!.tafsir
                        )
                    }

                })
        )
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.surahId = bundle.getString(Constant.Key.SURAH_NUMBER, "")
            this.position = bundle.getInt(Constant.Key.POSITION, 0)
        }
    }

    private fun dataSurah() {
        var detailSurahResponse: DetailSurahResponse? = null
        CompositeDisposable().add(
            ApiHelper
                .detailSurah(this.surahId)
                .subscribeWith(object : DisposableObserver<DetailSurahResponse>() {
                    override fun onNext(t: DetailSurahResponse) {
                        Log.d(TAG, "onNext()")
                        detailSurahResponse = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError()")
                        Toast.makeText(
                            this@DetailSurahActivity,
                            "Terjadi Kesalahan",
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete()")
                        this@DetailSurahActivity.binding.recyclerView.adapter = DetailSurahAdapter(
                            this@DetailSurahActivity,
                            detailSurahResponse!!,
                            detailSurahResponse!!.ayah
                        )
                        this@DetailSurahActivity.binding.let {

                            if (this@DetailSurahActivity.position != 0) {
                                it.recyclerView.smoothScrollToPosition(position)
                            }

                            this@DetailSurahActivity.audio = detailSurahResponse!!.audio
                            this@DetailSurahActivity.mediaPlayer.setDataSource(detailSurahResponse!!.audio)
                            this@DetailSurahActivity.mediaPlayer.prepare()

                            it.textSurahName.text = detailSurahResponse!!.latinName
                            it.textShortDesc.text =
                                "Ayat : ${detailSurahResponse!!.totalAyah} | Arti : ${detailSurahResponse!!.mean}"

                            it.buttonPlay.setOnClickListener(this@DetailSurahActivity)
                            this@DetailSurahActivity.changeActionBarTitle("Detail Surah ${detailSurahResponse!!.latinName}")

                        }

                    }

                })
        )
    }

    private fun init() {
        this.mediaPlayer = MediaPlayer()
        this.mediaPlayer.setOnCompletionListener {
            this.time = 0
            this.isPlaying = false
            this.mediaPlayer.stop()
            this.mediaPlayer.prepare()
            stopAudio()
        }

        this.binding.let {
            it.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.recyclerTafsir.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)

            it.buttonTafsir.setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonTafsir) {
            this.changeMode()
        } else if (p0 == this.binding.buttonPlay) {
            this.playAudio()
        }
    }

    private fun playAudio() {
        if (!isPlaying) {
            startAudio()
        } else {
            stopAudio()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (isPlaying) {
            this.mediaPlayer.stop()
            this.mediaPlayer.release()
        }
    }

    private fun stopAudio() {
        this.binding.apply {
            val drawable = resources.getDrawable(R.drawable.ic_round_play_arrow_24, null)
            drawable.setBounds(0, 0, 60, 60)
            this.buttonPlay.setCompoundDrawables(drawable, null, null, null)
            this.buttonPlay.setBackgroundColor(resources.getColor(R.color.bluePrimary))
            this.buttonPlay.text = "Play Audio"
        }

        if (this.mediaPlayer.isPlaying) {
            this.isPlaying = false
            this.mediaPlayer.pause()
            this.time = this.mediaPlayer.currentPosition
        }
    }

    private fun startAudio() {

        if (!this.mediaPlayer.isPlaying) {
            this.binding.apply {
                val drawable = resources.getDrawable(R.drawable.ic_round_stop_24, null)
                drawable.setBounds(0, 0, 60, 60)
                this.buttonPlay.setCompoundDrawables(drawable, null, null, null)
                this.buttonPlay.setBackgroundColor(Color.RED)
                this.buttonPlay.text = "Stop Audio"
            }

            this.isPlaying = true
            this.mediaPlayer.start()
            this.mediaPlayer.seekTo(time)
        }
    }

    private fun changeMode() {
        if (mode == 1) {
            mode = 2
            tafsirMode()
        } else {
            mode = 1
            surahMode()
        }
    }

    private fun tafsirMode() {
        this.binding.apply {
            this.buttonTafsir.text = "Lihat Surat"
            this.buttonPlay.visibility = View.GONE

            this.recyclerView.visibility = View.GONE
            this.recyclerTafsir.visibility = View.VISIBLE


            val surahName = this.textSurahName.text.toString()
            this@DetailSurahActivity.changeActionBarTitle("Tafsir Surah $surahName")
        }

    }

    private fun changeActionBarTitle(title: String) {

        if (this.supportActionBar != null) {
            this.supportActionBar!!.title = title
        }
    }

    private fun surahMode() {
        this.binding.apply {
            this.buttonTafsir.text = getString(R.string.see_tafsir)
            this.buttonPlay.visibility = View.VISIBLE

            this.recyclerTafsir.visibility = View.GONE
            this.recyclerView.visibility = View.VISIBLE

            val surahName = this.textSurahName.text.toString()
            this@DetailSurahActivity.changeActionBarTitle("Detail Surah $surahName")
        }
    }
}