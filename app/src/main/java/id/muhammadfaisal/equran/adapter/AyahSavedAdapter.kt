package id.muhammadfaisal.equran.adapter

import android.content.Context
import android.os.Bundle
import android.telephony.ims.ImsMmTelManager
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.activity.AyahSavedActivity
import id.muhammadfaisal.equran.activity.DetailSurahActivity
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.ItemDetailSurahBinding
import id.muhammadfaisal.equran.helper.DatabaseHelper
import id.muhammadfaisal.equran.helper.GeneralHelper
import id.muhammadfaisal.equran.utils.Constant

class AyahSavedAdapter(
    private val context: Context,
    private val ayahSaveds: List<AyahSavedEntity>
) : RecyclerView.Adapter<AyahSavedAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemDetailSurahBinding.bind(itemView)
        private val TAG = AyahSavedAdapter::class.java.simpleName

        private lateinit var ayahSavedEntity: AyahSavedEntity
        private lateinit var context: Context
        private var pos = 0

        fun bind(context: Context, ayahSavedEntity: AyahSavedEntity, position: Int) {
            this.ayahSavedEntity = ayahSavedEntity
            this.context = context
            this.pos = ayahSavedEntity.position

            this.binding.let {
                it.textIndonesian.text = ayahSavedEntity.indonesian
                it.textArabic.text = "${ayahSavedEntity.arabic} (${ayahSavedEntity.ayahNumber})"
                it.textLatin.text = "${Html.fromHtml(ayahSavedEntity.latin)} (${ayahSavedEntity.ayahNumber})"
                it.textAyahNumber.text = ayahSavedEntity.surahNumber

                it.buttonDelete.setOnClickListener(this)
            }

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (p0 == this.itemView) {
                this.move()
            } else {
                val ayahSavedDao = DatabaseHelper.ayahSavedDao(this.context)
                ayahSavedDao.delete(this.ayahSavedEntity)

                Toast.makeText(this.context, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                GeneralHelper.move(this.context, AyahSavedActivity::class.java, null, true)
            }
        }

        private fun move() {
            Log.d(TAG, "onItemClicked() Waiting for move...")
            val surahInfo = this.ayahSavedEntity.surahNumber.split(":")
            val surahId = surahInfo[1].replace(" ", "")

            val bundle = Bundle()
            bundle.putString(Constant.Key.SURAH_NUMBER, surahId)
            bundle.putInt(Constant.Key.POSITION, pos)

            GeneralHelper.move(this.context, DetailSurahActivity::class.java, bundle, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_detail_surah, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.ayahSaveds[position], position)
    }

    override fun getItemCount(): Int {
        return this.ayahSaveds.size
    }
}