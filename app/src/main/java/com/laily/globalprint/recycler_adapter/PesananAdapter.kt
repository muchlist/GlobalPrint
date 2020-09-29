package com.laily.globalprint.recycler_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laily.globalprint.R
import com.laily.globalprint.data.PesananDetailResponse
import com.laily.globalprint.utils.*
import kotlinx.android.synthetic.main.item_pesanan.view.*

class PesananAdapter(
    private val context: Context?,
    private val itemList: List<PesananDetailResponse>,
    private val itemClick: (PesananDetailResponse) -> Unit,
) : RecyclerView.Adapter<PesananAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(
            view,
            itemClick,
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }


    class ViewHolder(
        view: View,
        val editClick: (PesananDetailResponse) -> Unit,
    ) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: PesananDetailResponse) {

            itemView.apply {

                tv_item_nama_pesanan.text = items.pelanggan.namaPelanggan
                tv_item_id_pesanan.text = items.id
                tv_item_harga_pesanan.text = TransformInt().toRupiahString(items.biaya.totalBayar)
                tv_item_piutang_pesanan.text = "(${TransformInt().toRupiahString(items.biaya.sisaBayar)})"
                tv_item_judul_pesanan.text = items.namaPesanan
                tv_item_tanggal_pesanan.text = items.dibuat.toDate().toStringDateMonthYear()

                if (items.biaya.apakahLunas) {
                    tv_item_lunas_pesanan.visible()
                    tv_item_piutang_pesanan.invisible()
                } else {
                    tv_item_lunas_pesanan.invisible()
                    tv_item_piutang_pesanan.visible()
                }

                //onClick
                itemView.setOnClickListener { editClick(items) }
            }
        }
    }
}