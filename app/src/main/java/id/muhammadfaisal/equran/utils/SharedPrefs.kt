package id.muhammadfaisal.equran.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefs {
    companion object {
        private var sharedPreferences: SharedPreferences? = null

        private fun getSharedPreferences(context: Context): SharedPreferences? {
            if (this.sharedPreferences == null) {
                this.sharedPreferences = context.getSharedPreferences(
                    Constant.Key.SHARED_PREFERENCE_NAME,
                    Context.MODE_PRIVATE
                )
            }
            return this.sharedPreferences
        }
        fun save(context: Context, key: String, value: Any) {
            if (this.sharedPreferences == null) {
                this.sharedPreferences = getSharedPreferences(context)
            }

            val editor = this.sharedPreferences!!.edit()
            when (value) {
                is String -> {
                    Log.d("SharedPreference", "String data type for $value")
                    editor.putString(key, value)
                }
                is Long -> {
                    Log.d("SharedPreference", "Long data type for $value")
                    editor.putLong(key, value)
                }
                is Boolean -> {
                    Log.d("SharedPreference", "Boolean data type for $value")
                    editor.putBoolean(key, value)
                }
                is Int -> {
                    Log.d("SharedPreference", "Int data type for $value")
                    editor.putInt(key, value)
                }
            }

            editor.apply()
        }

        fun get(context : Context, key : String) : Any? {
            val sharedPreferences = context.getSharedPreferences(Constant.Key.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.all[key]
        }
    }
}