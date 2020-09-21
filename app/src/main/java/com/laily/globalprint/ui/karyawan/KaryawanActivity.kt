package com.laily.globalprint.ui.karyawan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.UserListResponse
import com.laily.globalprint.recycler_adapter.KaryawanAdapter
import kotlinx.android.synthetic.main.activity_karyawan.*

class KaryawanActivity : AppCompatActivity() {

    private lateinit var viewModel: KaryawanViewModel

    //recyclerview
    private lateinit var karyawanAdapter: KaryawanAdapter
    private var dataKaryawan: MutableList<UserListResponse.User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_karyawan)

        viewModel = ViewModelProvider(this).get(KaryawanViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        refresh_karyawan.setOnRefreshListener {
            //menghapus text di pencarian saat refresh layout
            loadKaryawan()
        }

        //init computers
        loadKaryawan()

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataKaryawan().observe(this@KaryawanActivity, {
                memunculkanDataDiRecyclerView(it)
            })
            isLoading.observe(this@KaryawanActivity, { menampilkanLoading(it) })
            messageError.observe(this@KaryawanActivity, { menampilkanToastError(it) })
        }
    }

    private fun loadKaryawan() {
        viewModel.mendapatkanUserDariServer()
    }

    private fun setRecyclerView() {
        rv_karyawan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        karyawanAdapter = KaryawanAdapter(this, dataKaryawan)
        rv_karyawan.adapter = karyawanAdapter
        rv_karyawan.setHasFixedSize(true)
    }

    private fun memunculkanDataDiRecyclerView(data: UserListResponse) {
        dataKaryawan.clear()
        dataKaryawan.addAll(data.user)
        karyawanAdapter.notifyDataSetChanged()
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        refresh_karyawan.isRefreshing = isLoading
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

}