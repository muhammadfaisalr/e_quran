package id.muhammadfaisal.equran.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.adapter.SurahAdapter
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.ActivityMainBinding
import id.muhammadfaisal.equran.helper.DatabaseHelper
import id.muhammadfaisal.equran.helper.GeneralHelper

class MainActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        this.init()
    }

    private fun init() {
        val surahDao = DatabaseHelper.surahDao(this)

        this.binding.let {
            it.recyclerView.layoutManager = GridLayoutManager(this, 2)
            it.recyclerView.adapter = SurahAdapter(this, surahDao.getAll())

            it.inputSearchName.addTextChangedListener(this)
            it.buttonAyahSaved.setOnClickListener(this)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        val string = p0.toString()
        val surahDao = DatabaseHelper.surahDao(this)

        this.binding.let {
            if (string.isNotEmpty()) {
                it.recyclerView.adapter = SurahAdapter(this, surahDao.query(string))
            }  else {
                it.recyclerView.adapter = SurahAdapter(this, surahDao.getAll())
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0 == this.binding.buttonAyahSaved) {
            GeneralHelper.move(this, AyahSavedActivity::class.java, null, false)
        }
    }
}