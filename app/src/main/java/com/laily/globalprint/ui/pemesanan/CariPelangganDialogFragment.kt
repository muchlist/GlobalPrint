package com.laily.globalprint.ui.pemesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import com.laily.globalprint.data.PelangganListResponse
import com.laily.globalprint.recycler_adapter.CariPelangganAdapter
import kotlinx.android.synthetic.main.fragment_dialog_cari_pelanggan.*
import kotlinx.android.synthetic.main.fragment_dialog_cari_pelanggan.view.*

class CariPelangganDialogFragment : DialogFragment() {

    private lateinit var viewModel: TambahPesananViewModel

    //recyclerview
    private lateinit var pelangganAdapter: CariPelangganAdapter
    private var dataPelanggan: MutableList<PelangganDetailResponse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_dialog_cari_pelanggan, container, false)
//        rootView.cancel_button.setOnClickListener(){
//            //dismiss()
//        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TambahPesananViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        memantauPerubahanDiSearchBar()
    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataPelanggan().observe(viewLifecycleOwner, {
                memunculkanDataDiRecyclerView(it)
            })
            isLoading.observe(viewLifecycleOwner, { menampilkanLoading(it) })
            messageError.observe(viewLifecycleOwner, { menampilkanToastError(it) })
        }
    }

    private fun setRecyclerView() {
        rv_pelanggan.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        pelangganAdapter = CariPelangganAdapter(requireActivity(), dataPelanggan) {
            viewModel.pilihPelanggan(it)
            dismiss()
        }
        rv_pelanggan.adapter = pelangganAdapter
        rv_pelanggan.setHasFixedSize(true)
    }

    private fun memunculkanDataDiRecyclerView(data: PelangganListResponse) {
        dataPelanggan.clear()
        dataPelanggan.addAll(data.pelanggan)
        pelangganAdapter.notifyDataSetChanged()
    }

    private fun memantauPerubahanDiSearchBar() {
        et_cari_pelanggan.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                loadPelanggan(nama = newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    loadPelanggan(nama = query)
                }
                return false
            }
        })
    }

    private fun loadPelanggan(nama: String = "") {
        viewModel.mendapatkanPelangganDariServer(nama)
    }

    private fun menampilkanLoading(isLoading: Boolean) {
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
        }
    }

}