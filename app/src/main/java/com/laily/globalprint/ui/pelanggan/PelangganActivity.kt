package com.laily.globalprint.ui.pelanggan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import com.laily.globalprint.data.PelangganListResponse
import com.laily.globalprint.recycler_adapter.PelangganAdapter
import com.laily.globalprint.utils.App
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
            startActivity(Intent(this, TambahPelangganActivity::class.java))
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
            isPelangganDeleted.observe(this@PelangganActivity, {
                if (it){
                    loadPelanggan()
                    menampilkanToastError("Mengahapus pelanggan berhasil")
                }
            })
        }
    }

    private fun loadPelanggan(nama: String = "") {
        viewModel.mendapatkanPelangganDariServer(nama)
    }

    private fun setRecyclerView() {
        rv_pelanggan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val editClick: (PelangganDetailResponse) -> Unit = {
            val intent = Intent(this, EditPelangganActivity::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }

        val hapusClick: (PelangganDetailResponse) -> Unit =
            { memunculkanDialogHapusPelanggan(it.id) }

        pelangganAdapter = PelangganAdapter(this, dataPelanggan, editClick, hapusClick)
        rv_pelanggan.adapter = pelangganAdapter
        rv_pelanggan.setHasFixedSize(true)
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

    private fun memunculkanDialogHapusPelanggan(pelangganID: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Konfirmasi")
        builder.setMessage("Yakin ingin menghapus pelanggan?")

        builder.setPositiveButton("Ya") { _, _ ->
            viewModel.menghapusPelangganDariServer(pelangganID)
        }
        builder.setNeutralButton("Batal") { _, _ ->

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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

    override fun onResume() {
        super.onResume()
        if (App.activityListPelangganHarusDiRefresh) {
            loadPelanggan()
            App.activityListPelangganHarusDiRefresh = false
        }
    }
}