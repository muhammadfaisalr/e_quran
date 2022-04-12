package id.muhammadfaisal.equran.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import id.muhammadfaisal.equran.databinding.ActivitySplashScreenBinding
import id.muhammadfaisal.equran.helper.GeneralHelper

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySplashScreenBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        this.supportActionBar!!.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            GeneralHelper.move(this, MainActivity::class.java, null, true)
        }, 3000L)
    }
}