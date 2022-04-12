package id.muhammadfaisal.equran.adapter

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.api.model.AyatItem
import id.muhammadfaisal.equran.api.model.DetailSurahResponse
import id.muhammadfaisal.equran.api.model.TafsirItem
import id.muhammadfaisal.equran.bottomsheet.MenuBottomSheetDialogFragment
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.ItemDetailSurahBinding
import id.muhammadfaisal.equran.utils.Constant

class TafsirAdapter(
    private val context: Context,
    private val detailSurahResponse: DetailSurahResponse,
    private val tafsir: List<TafsirItem>
) : RecyclerView.Adapter<TafsirAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemDetailSurahBinding.bind(itemView)
        private lateinit var tafsirItem: TafsirItem
        private lateinit var context: Context
        private lateinit var detailSurah: DetailSurahResponse

        private var pos = 0

        fun bind(
            context: Context,
            detailSurahResponse: DetailSurahResponse,
            tafsirItem: TafsirItem,
            position: Int
        ) {
            this.pos = position
            this.tafsirItem = tafsirItem
            this.context = context
            this.detailSurah = detailSurahResponse

            this.binding.let {
                it.textAyahNumber.text = "Ayat ${tafsirItem.ayat}"
                it.textLatin.text = tafsirItem.tafsir

                it.textArabic.visibility = View.GONE
                it.textIndonesian.visibility = View.GONE
                it.buttonDelete.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_surah, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.detailSurahResponse, this.tafsir[position], position)
    }

    override fun getItemCount(): Int {
        return this.tafsir.size
    }
}