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
import id.muhammadfaisal.equran.bottomsheet.MenuBottomSheetDialogFragment
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.ItemDetailSurahBinding
import id.muhammadfaisal.equran.utils.Constant

class DetailSurahAdapter(
    private val context: Context,
    private val detailSurahResponse: DetailSurahResponse,
    private val ayah: List<AyatItem>
) : RecyclerView.Adapter<DetailSurahAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemDetailSurahBinding.bind(itemView)
        private lateinit var ayatItem: AyatItem
        private lateinit var context: Context
        private lateinit var detailSurah: DetailSurahResponse

        private var pos = 0

        fun bind(
            context: Context,
            detailSurahResponse: DetailSurahResponse,
            ayatItem: AyatItem,
            position: Int
        ) {
            this.pos = position
            this.ayatItem = ayatItem
            this.context = context
            this.detailSurah = detailSurahResponse

            this.binding.let {
                it.textArabic.text = ayatItem.arabic
                it.textAyahNumber.text = ayatItem.number
                it.textLatin.text = Html.fromHtml(ayatItem.translate, Html.FROM_HTML_MODE_COMPACT)
                it.textIndonesian.text = ayatItem.indonesian

                it.buttonDelete.visibility = View.GONE
            }

            this.itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val surahInfo = this.detailSurah.latinName + " : " + this.detailSurah.number
            val ayahSavedEntity = AyahSavedEntity(
                null,
                surahInfo,
                this.ayatItem.number,
                this.ayatItem.arabic,
                this.ayatItem.translate,
                this.ayatItem.indonesian,
                this.pos
            )

            val bundle = Bundle()
            bundle.putSerializable(Constant.Key.SURAH, ayahSavedEntity)

            val bottomSheet = MenuBottomSheetDialogFragment()
            bottomSheet.arguments = bundle
            bottomSheet.show(
                (context as AppCompatActivity).supportFragmentManager,
                DetailSurahAdapter::class.simpleName
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_surah, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.detailSurahResponse, this.ayah[position], position)
    }

    override fun getItemCount(): Int {
        return this.ayah.size
    }
}