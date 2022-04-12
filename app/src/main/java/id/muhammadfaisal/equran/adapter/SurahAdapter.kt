package id.muhammadfaisal.equran.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.activity.DetailSurahActivity
import id.muhammadfaisal.equran.database.entity.SurahEntity
import id.muhammadfaisal.equran.databinding.ItemSurahBinding
import id.muhammadfaisal.equran.helper.GeneralHelper
import id.muhammadfaisal.equran.utils.Constant

class SurahAdapter(private val context: Context, private val surah: List<SurahEntity>) : RecyclerView.Adapter<SurahAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemSurahBinding.bind(itemView)
        private var context: Context? = null
        private var surahId = 0

        fun bind(context: Context, surahEntity: SurahEntity, position: Int) {
            val currentPosition = position + 1

            this.context = context
            this.surahId = surahEntity.id

            this.binding.let {
                it.textIndonesian.text = surahEntity.mean
                it.textArabic.text = surahEntity.name
                it.textSurahName.text = "${currentPosition}. ${surahEntity.latinName}"
            }

            this.itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val bundle = Bundle()
            bundle.putString(Constant.Key.SURAH_NUMBER, this.surahId.toString())
            GeneralHelper.move(this.context!!, DetailSurahActivity::class.java, bundle, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.surah[position], position)
    }

    override fun getItemCount(): Int {
        return this.surah.size
    }
}