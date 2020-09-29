package com.laily.globalprint.ui.pemesanan

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

    //Dropdown filter lunas
    private lateinit var myDialog: Dialog

    private var lunasFilter = ""
    private var namaFilter = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)

        viewModel = ViewModelProvider(this).get(PesananViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        //INIT dialog
        myDialog = Dialog(this)

        refresh_pesanan.setOnRefreshListener {
            //menghapus text di pencarian saat refresh layout
            //et_cari_pesanan.setQuery("", false)
            loadPesanan()
        }

        loadPesanan()

        memantauPerubahanDiSearchBar()

        fab_pesanan.setOnClickListener {
            startActivity(Intent(this, TambahPesananActivity::class.java))
        }

        chip4.setOnClickListener {
            tampilkanFilterDialog()
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
                if (it) {
                    loadPesanan()
                    menampilkanToastError("Mengahapus pesanan berhasil")
                }
            })
        }
    }

    private fun loadPesanan() {
        val lunasValid = when (lunasFilter) {
            "SEMUA" -> ""
            "LUNAS" -> "1"
            "BELUM LUNAS" -> "0"
            else -> ""
        }
        viewModel.mendapatkanPesananDariServer(namaFilter, lunasValid)
    }

    private fun setRecyclerView() {
        rv_pesanan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val itemClick: (PesananDetailResponse) -> Unit = {
            val intent = Intent(this, PesananDetailActivity::class.java)
            intent.putExtra("pesanan_id", it.id)
            startActivity(intent)
        }

        pesananAdapter = PesananAdapter(this, dataPesanan, itemClick)
        rv_pesanan.adapter = pesananAdapter
        rv_pesanan.setHasFixedSize(true)
    }

    private fun memantauPerubahanDiSearchBar() {
        et_cari_pesanan.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                namaFilter = newText
                loadPesanan()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    namaFilter = query
                    loadPesanan()
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

    private fun menghitungTotalPendapatan(data: PesananListResponse) {
        var total = 0
        var totalPiutang = 0
        for (pesanan in data.pesanan) {
            total += pesanan.biaya.totalBayar
            totalPiutang += pesanan.biaya.sisaBayar
        }
        tv_total_pendapatan.text =
            "Total : ${TransformInt().toRupiahString(total)}\nTotal Piutang : ${
                TransformInt().toRupiahString(totalPiutang)
            }"
    }

    private fun tampilkanFilterDialog() {

        //mengisi semua opsi untuk spinner
        val pilihanLunas: MutableList<String> = mutableListOf()
        pilihanLunas.add(0, "SEMUA")
        pilihanLunas.add(1, "LUNAS")
        pilihanLunas.add(2, "BELUM LUNAS")

        //Mendapatkan index dari isian awal
        val pilihanLunasIndex = pilihanLunas.indexOf(lunasFilter)

        //Nilai yang terpilih
        var lunasTerpilih = lunasFilter

        //set layout untuk dialog
        myDialog.setContentView(R.layout.dialog_filter)

        //INISIASI Spinner
        val lunasDropdown: Spinner = myDialog.findViewById(R.id.sp_filter_lunas)
        lunasDropdown.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pilihanLunas)

        lunasDropdown.setSelection(pilihanLunasIndex)
        lunasDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lunasTerpilih = pilihanLunas[position]
            }

        }

        //TOMBOL APPLY
        val buttonDialogFilter: Button =
            myDialog.findViewById(R.id.bt_filter_apply)
        buttonDialogFilter.setOnClickListener {

            lunasFilter = lunasTerpilih
            loadPesanan()
            myDialog.dismiss()
        }

        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
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