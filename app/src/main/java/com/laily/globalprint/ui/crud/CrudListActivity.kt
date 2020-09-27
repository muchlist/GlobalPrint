package com.laily.globalprint.ui.crud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laily.globalprint.R
import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.recycler_adapter.CrudAdapter
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_crud_list.*

class CrudListActivity : AppCompatActivity() {

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
            //TODO
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
//        val editClick: (PelangganDetailResponse) -> Unit = {
//            val intent = Intent(this, EditPelangganActivity::class.java)
//            intent.putExtra("data", it)
//            startActivity(intent)
//        }

//        val hapusClick: (PelangganDetailResponse) -> Unit =
//            { memunculkanDialogHapusPelanggan(it.id) }

        crudAdapter = CrudAdapter(this, dataCrud)
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
        if (App.activityListPelangganHarusDiRefresh) {
            viewModel.mendapatkanListCrudDariServer()
            App.activityListCrudHarusDiRefresh = false
        }
    }
}