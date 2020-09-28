package com.laily.globalprint.ui.pemesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.data.BahanListResponse
import com.laily.globalprint.recycler_adapter.BahanAdapter
import kotlinx.android.synthetic.main.fragment_dialog_cari_bahan.*

class CariBahanDialogFragment : DialogFragment() {

    private lateinit var viewModel: TambahPesananViewModel

    //recyclerview
    private lateinit var bahanAdapter: BahanAdapter
    private var dataBahan: MutableList<BahanDetailResponse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_dialog_cari_bahan, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TambahPesananViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        memantauPerubahanDiSearchBar()

        loadBahan("")
    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataBahan().observe(viewLifecycleOwner, {
                memunculkanDataDiRecyclerView(it)
            })
            isLoading.observe(viewLifecycleOwner, { menampilkanLoading(it) })
            messageError.observe(viewLifecycleOwner, { menampilkanToastError(it) })
        }
    }

    private fun setRecyclerView() {
        rv_bahan.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        bahanAdapter = BahanAdapter(requireActivity(), dataBahan) {
            viewModel.pilihBahan(it)
            dismiss()
        }
        rv_bahan.adapter = bahanAdapter
        rv_bahan.setHasFixedSize(true)
    }

    private fun memunculkanDataDiRecyclerView(data: BahanListResponse) {
        dataBahan.clear()
        dataBahan.addAll(data.bahan)
        bahanAdapter.notifyDataSetChanged()
    }

    private fun memantauPerubahanDiSearchBar() {
        et_cari_bahan.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                loadBahan(nama = newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    loadBahan(nama = query)
                }
                return false
            }
        })
    }

    private fun loadBahan(nama: String = "") {
        viewModel.mendapatkanBahanDariServer(nama)
    }

    private fun menampilkanLoading(isLoading: Boolean) {
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
        }
    }

}