package id.muhammadfaisal.equran.helper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.equran.utils.Constant

class GeneralHelper {
    companion object {
        fun move(context: Context, clazz: Class<*>, bundle: Bundle?, isFinish: Boolean) {
            val intent = Intent(context, clazz)

            if (bundle != null) {
                intent.putExtra(Constant.Key.BUNDLING, bundle)
            }

            context.startActivity(intent)

            if (isFinish) {
                (context as AppCompatActivity).finish()
            }
        }
    }
}