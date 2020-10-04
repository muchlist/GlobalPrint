package com.laily.globalprint.ui.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.recycler_adapter.CrudAdapter
import com.laily.globalprint.ui.karyawan.TambahKaryawanActivity
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_crud_list.*

class CrudListActivity : AppCompatActivity() {

    //View model dibuat global karena diakses oleh fungsi lainnya didalam class selain di fungsi onCreate
    private lateinit var viewModel: CrudViewModel

    //recyclerview
    private lateinit var crudAdapter: CrudAdapter
    private var dataCrud: MutableList<CrudListResponse.Crud> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_list)

        viewModel = ViewModelProvider(this).get(CrudViewModel::class.java)

        mengawasiViewModel()

        setRecyclerView()

        fab_crud.setOnClickListener {
            startActivity(Intent(this, TambahCrudActivity::class.java))
        }

        refresh_crud.setOnRefreshListener {
            viewModel.mendapatkanListCrudDariServer()
        }

        viewModel.mendapatkanListCrudDariServer()
    }

    private fun setRecyclerView() {
        rv_crud.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //TODO onclik crud
        val editClick: (CrudListResponse.Crud) -> Unit = {
            val intent = Intent(this, TambahCrudActivity::class.java)
            intent.putExtra("crud_data", it)
            intent.putExtra("form_crud", "Edit Crud")
            intent.putExtra("mode_crud", 1)
            intent.putExtra("id_crud", it.id)
            startActivity(intent)
        }

        val hapusClick: (CrudListResponse.Crud) -> Unit =
            { viewModel.menghapusCrudDariServer(id = it.id) }

        crudAdapter = CrudAdapter(this, dataCrud,editClick,hapusClick)
        rv_crud.adapter = crudAdapter
        rv_crud.setHasFixedSize(true)
    }

    private fun mengawasiViewModel() {

        viewModel.isLoading.observe(this, { menampilkanLoading(it) })
        viewModel.messageError.observe(this, { menampilkanToastError(it) })
        viewModel.isCrudDeleted.observe(this, {
            if (it) {
                viewModel.mendapatkanListCrudDariServer()
                menampilkanToastError("Mengahapus pelanggan berhasil")
            }
        })

        viewModel.getDataCrud().observe(this, {
            memunculkanDataDiRecyclerView(it)
        })
    }

    private fun memunculkanDataDiRecyclerView(data: CrudListResponse) {
        dataCrud.clear()
        dataCrud.addAll(data.crud)
        crudAdapter.notifyDataSetChanged()
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        refresh_crud.isRefreshing = isLoading
    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (App.activityListCrudHarusDiRefresh) {
            viewModel.mendapatkanListCrudDariServer()
            App.activityListCrudHarusDiRefresh = false
        }
    }
}