package com.laily.globalprint.ui.pelanggan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import kotlinx.android.synthetic.main.item_pelanggan.view.*

class PelangganAdapter(
    private val context: Context?,
    private val itemList: List<PelangganDetailResponse>,
    private val editClick: (PelangganDetailResponse) -> Unit,
    private val hapusClick: (PelangganDetailResponse) -> Unit,
) : RecyclerView.Adapter<PelangganAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_pelanggan, parent, false)
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


    class ViewHolder(view: View,
                     val editClick: (PelangganDetailResponse) -> Unit,
                     val hapusClick: (PelangganDetailResponse) -> Unit,
    ) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: PelangganDetailResponse) {

            itemView.apply {
                tv_item_id_pelanggan.text = items.idPelanggan
                tv_item_nama_pelanggan.text = items.nama
                tv_item_email_pelanggan.text = items.email
                tv_item_no_hp_pelanggan.text = items.hp
                tv_item_alamat_pelanggan.text = items.alamat

                //onClick
                bt_item_edit_pelanggan.setOnClickListener { editClick(items) }
                bt_item_hapus_pelanggan.setOnLongClickListener{
                    hapusClick(items)
                    false
                }
            }
        }
    }
}