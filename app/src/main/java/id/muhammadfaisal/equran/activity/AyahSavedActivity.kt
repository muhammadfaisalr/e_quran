package id.muhammadfaisal.equran.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.adapter.AyahSavedAdapter
import id.muhammadfaisal.equran.databinding.ActivityAyahSavedBinding
import id.muhammadfaisal.equran.helper.DatabaseHelper

class AyahSavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAyahSavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAyahSavedBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        this.supportActionBar.let {
            it!!.title = "Ayat Disimpan"
            it.setDefaultDisplayHomeAsUpEnabled(true)
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        this.init()
    }

    private fun init() {
        val ayahDao = DatabaseHelper.ayahSavedDao(this)
        this.binding.let {
            it.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.recyclerView.adapter = AyahSavedAdapter(this, ayahDao.getAll())
        }
    }
}