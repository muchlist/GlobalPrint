package com.laily.globalprint.ui.bahan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.data.BahanListResponse
import com.laily.globalprint.recycler_adapter.BahanAdapter
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_bahan.*

class BahanActivity : AppCompatActivity() {

    private lateinit var viewModel: BahanViewModel

    //recyclerview
    private lateinit var bahanAdapter: BahanAdapter
    private var dataBahan: MutableList<BahanDetailResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bahan)

        viewModel = ViewModelProvider(this).get(BahanViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        refresh_bahan.setOnRefreshListener {
            //menghapus text di pencarian saat refresh layout
            et_cari_bahan.setQuery("", false)
            loadBahan()
        }

        //init computers
        loadBahan()

        memantauPerubahanDiSearchBar()

        fab_bahan.setOnClickListener {
            //TODO startActivity(Intent(this, TambahBahanActivity::class.java))
        }

        // Menyembunyikan KEYBOARD
        et_cari_bahan.isFocusable = false
        et_cari_bahan.clearFocus()

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataBahan().observe(this@BahanActivity, {
                memunculkanDataDiRecyclerView(it)
            })
            isLoading.observe(this@BahanActivity, { menampilkanLoading(it) })
            messageError.observe(this@BahanActivity, { menampilkanToastError(it) })
        }
    }

    private fun loadBahan(nama: String = "") {
        viewModel.mendapatkanBahanDariServer(nama)
    }

    private fun setRecyclerView() {
        rv_bahan.layoutManager =
            GridLayoutManager(this, 2)


        val editClick: (BahanDetailResponse) -> Unit = {
//            val intent = Intent(this, EditBahanActivity::class.java)
//            intent.putExtra("data", it)
//            startActivity(intent)
        }

        bahanAdapter = BahanAdapter(this, dataBahan, editClick)
        rv_bahan.adapter = bahanAdapter
        rv_bahan.setHasFixedSize(true)
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


    private fun memunculkanDataDiRecyclerView(data: BahanListResponse) {
        dataBahan.clear()
        dataBahan.addAll(data.bahan)
        bahanAdapter.notifyDataSetChanged()
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        refresh_bahan.isRefreshing = isLoading
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (App.activityListBahanHarusDiRefresh) {
            loadBahan()
            App.activityListBahanHarusDiRefresh = false
        }
    }
}