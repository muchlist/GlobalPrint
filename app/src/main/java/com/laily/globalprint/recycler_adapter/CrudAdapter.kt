package com.laily.globalprint.recycler_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laily.globalprint.R
import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.data.PelangganDetailResponse
import kotlinx.android.synthetic.main.item_crud.view.*

class CrudAdapter(
    private val context: Context?,
    private val itemList: List<CrudListResponse.Crud>,
    private val editClick: (CrudListResponse.Crud) -> Unit,
    private val hapusClick: (CrudListResponse.Crud) -> Unit,
) : RecyclerView.Adapter<CrudAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_crud, parent, false)
        return ViewHolder(
            view,
            editClick,
            hapusClick,
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }


    class ViewHolder(
        view: View,
        val editClick: (CrudListResponse.Crud) -> Unit,
        val hapusClick: (CrudListResponse.Crud) -> Unit,
    ) :
        RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindItem(items: CrudListResponse.Crud) {

            itemView.apply {
                tv_crud_nama.text = items.nama
                tv_crud_alamat.text = items.alamat
                tv_crud_keterangan.text = items.keterangan
            }

            itemView.iv_crud_hapus.setOnLongClickListener {
                hapusClick(items)
                false
            }

            itemView.iv_crud_edit.setOnClickListener {
                editClick(items)
            }
        }
    }
}