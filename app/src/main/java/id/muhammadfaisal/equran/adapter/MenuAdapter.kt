package id.muhammadfaisal.equran.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.bottomsheet.MenuBottomSheetDialogFragment
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.ItemMenuBinding
import id.muhammadfaisal.equran.helper.DatabaseHelper

class MenuAdapter(
    private val context: Context,
    private val fragment: MenuBottomSheetDialogFragment,
    private val strings: ArrayList<String>,
    private val ayatItem: AyahSavedEntity
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var binding = ItemMenuBinding.bind(itemView)
        private var TAG = MenuAdapter::class.simpleName

        private lateinit var context: Context
        private lateinit var ayatItem: AyahSavedEntity
        private lateinit var fragment: MenuBottomSheetDialogFragment

        fun bind(
            context: Context,
            fragment: MenuBottomSheetDialogFragment,
            s: String,
            ayatItem: AyahSavedEntity
        ) {
            this.fragment = fragment
            this.context = context
            this.ayatItem = ayatItem

            this.binding.textView.text = s
            this.itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            this.save()
        }

        private fun save() {
            Log.d(TAG, "Process Saving Data()")

            val ayahDao = DatabaseHelper.ayahSavedDao(this.context)
            ayahDao.insert(this.ayatItem)

            this.fragment.dismiss()

            Toast.makeText(this.context, "Berhasil Di Simpan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.fragment, this.strings[position], this.ayatItem)
    }

    override fun getItemCount(): Int {
        return this.strings.size
    }
}