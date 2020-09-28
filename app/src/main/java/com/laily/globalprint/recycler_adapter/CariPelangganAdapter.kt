package com.laily.globalprint.recycler_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import kotlinx.android.synthetic.main.item_cari_pelanggan.view.*

class CariPelangganAdapter(
    private val context: Context?,
    private val itemList: List<PelangganDetailResponse>,
    private val itemClick: (PelangganDetailResponse) -> Unit,
) : RecyclerView.Adapter<CariPelangganAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_cari_pelanggan, parent, false)
        return ViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }


    class ViewHolder(view: View,
                     val itemClick: (PelangganDetailResponse) -> Unit,
    ) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: PelangganDetailResponse) {

            itemView.apply {
                tv_item_nama_pelanggan.text = items.nama
                tv_item_no_hp_pelanggan.text = items.hp

                //onClick
                itemView.setOnClickListener {
                    itemClick(items)
                }
            }
        }
    }
}