package com.laily.globalprint.ui.pelanggan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import com.laily.globalprint.data.PelangganListResponse
import kotlinx.android.synthetic.main.activity_pelanggan.*

class PelangganActivity : AppCompatActivity() {

    private lateinit var viewModel: PelangganViewModel

    //recyclerview
    private lateinit var pelangganAdapter: PelangganAdapter
    private var dataPelanggan: MutableList<PelangganDetailResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelanggan)

        viewModel = ViewModelProvider(this).get(PelangganViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        refresh_pelanggan.setOnRefreshListener {
            //menghapus text di pencarian saat refresh layout
            et_cari_pelanggan.setQuery("", false)
            loadPelanggan()
        }

        //init computers
        loadPelanggan()

        memantauPerubahanDiSearchBar()

        fab_pelanggan.setOnClickListener {
            //TODO menujuKeAktifityMenambahkanPelanggan()
        }

        // Menyembunyikan KEYBOARD
        et_cari_pelanggan.isFocusable = false
        et_cari_pelanggan.clearFocus()

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataPelanggan().observe(this@PelangganActivity, {
                memunculkanDataDiRecyclerView(it)
            })
            isLoading.observe(this@PelangganActivity, { menampilkanLoading(it) })
            messageError.observe(this@PelangganActivity, { menampilkanToastError(it) })
        }
    }

    private fun loadPelanggan(nama: String = "") {
        viewModel.mendapatkanPelangganDariServer(nama)
    }

    private fun setRecyclerView() {
        rv_pelanggan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //TODO ganti klik
        val editClick: (PelangganDetailResponse) -> Unit = { menampilkanToastError("edit") }
        val hapusClick: (PelangganDetailResponse) -> Unit = { menampilkanToastError("edit") }

        pelangganAdapter = PelangganAdapter(this, dataPelanggan, editClick, hapusClick)
        rv_pelanggan.adapter = pelangganAdapter
        rv_pelanggan.setHasFixedSize(true)
    }

    private fun memantauPerubahanDiSearchBar() {
        et_cari_pelanggan.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
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

    private fun memunculkanDataDiRecyclerView(data: PelangganListResponse) {
        dataPelanggan.clear()
        dataPelanggan.addAll(data.pelanggan)
        pelangganAdapter.notifyDataSetChanged()
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        refresh_pelanggan.isRefreshing = isLoading
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

}