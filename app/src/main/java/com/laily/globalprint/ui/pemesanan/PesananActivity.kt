package com.laily.globalprint.ui.pemesanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.PesananDetailResponse
import com.laily.globalprint.data.PesananListResponse
import com.laily.globalprint.recycler_adapter.PesananAdapter
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.TransformInt
import kotlinx.android.synthetic.main.activity_pesanan.*

class PesananActivity : AppCompatActivity() {

    private lateinit var viewModel: PesananViewModel

    //recyclerview
    private lateinit var pesananAdapter: PesananAdapter
    private var dataPesanan: MutableList<PesananDetailResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)

        viewModel = ViewModelProvider(this).get(PesananViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        refresh_pesanan.setOnRefreshListener {
            //menghapus text di pencarian saat refresh layout
            et_cari_pesanan.setQuery("", false)
            loadPesanan()
        }

        //init computers
        loadPesanan()

        memantauPerubahanDiSearchBar()

        fab_pesanan.setOnClickListener {
            startActivity(Intent(this, TambahPesananActivity::class.java))
        }

        // Menyembunyikan KEYBOARD
        et_cari_pesanan.isFocusable = false
        et_cari_pesanan.clearFocus()

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataPesanan().observe(this@PesananActivity, {
                it?.let {
                    memunculkanDataDiRecyclerView(it)
                    menghitungTotalPendapatan(it)
                }
            })
            isLoading.observe(this@PesananActivity, { menampilkanLoading(it) })
            messageError.observe(this@PesananActivity, { menampilkanToastError(it) })
            isPesananDeleted.observe(this@PesananActivity, {
                if (it){
                    loadPesanan()
                    menampilkanToastError("Mengahapus pesanan berhasil")
                }
            })
        }
    }

    private fun loadPesanan(nama: String = "") {
        viewModel.mendapatkanPesananDariServer(nama)
    }

    private fun setRecyclerView() {
        rv_pesanan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val itemClick: (PesananDetailResponse) -> Unit = {
//            val intent = Intent(this, EditPesananActivity::class.java)
//            intent.putExtra("data", it)
//            startActivity(intent)
        }

        pesananAdapter = PesananAdapter(this, dataPesanan, itemClick)
        rv_pesanan.adapter = pesananAdapter
        rv_pesanan.setHasFixedSize(true)
    }

    private fun memantauPerubahanDiSearchBar() {
        et_cari_pesanan.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                loadPesanan(nama = newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    loadPesanan(nama = query)
                }
                return false
            }
        })
    }

    private fun memunculkanDataDiRecyclerView(data: PesananListResponse) {
        dataPesanan.clear()
        dataPesanan.addAll(data.pesanan)
        pesananAdapter.notifyDataSetChanged()
    }

    private fun menghitungTotalPendapatan(data: PesananListResponse){
        var total = 0
        var totalPiutang = 0
        for (pesanan in data.pesanan){
            total += pesanan.biaya.totalBayar
            totalPiutang += pesanan.biaya.sisaBayar
        }
        tv_total_pendapatan.text = "Total : ${TransformInt().toRupiahString(total)}\nTotal Piutang : ${TransformInt().toRupiahString(totalPiutang)}"
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        refresh_pesanan.isRefreshing = isLoading
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (App.activityListPesananHarusDiRefresh) {
            loadPesanan()
            App.activityListPesananHarusDiRefresh = false
        }
    }
}