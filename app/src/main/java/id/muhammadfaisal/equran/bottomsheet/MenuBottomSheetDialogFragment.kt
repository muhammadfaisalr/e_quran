package id.muhammadfaisal.equran.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.equran.adapter.MenuAdapter
import id.muhammadfaisal.equran.database.entity.AyahSavedEntity
import id.muhammadfaisal.equran.databinding.FragmentMenuBottomSheetDialogBinding
import id.muhammadfaisal.equran.utils.Constant

class MenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMenuBottomSheetDialogBinding
    private lateinit var menus: ArrayList<String>
    private lateinit var ayahSaved: AyahSavedEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentMenuBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.extract()
        this.init()
    }

    private fun extract() {
        this.ayahSaved = requireArguments().getSerializable(Constant.Key.SURAH) as AyahSavedEntity
    }

    private fun init() {
        this.menus = ArrayList()
        this.menus.add("Simpan")

        this.binding.let {
            it.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.recyclerView.adapter = MenuAdapter(requireContext(), this, this.menus, this.ayahSaved)
        }
    }
}