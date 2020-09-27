package com.laily.globalprint.recycler_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.utils.INTERNET_SERVER
import com.laily.globalprint.utils.TransformInt
import kotlinx.android.synthetic.main.item_bahan.view.*

class BahanAdapter(
    private val context: Context?,
    private val itemList: List<BahanDetailResponse>,
    private val editClick: (BahanDetailResponse) -> Unit,
) : RecyclerView.Adapter<BahanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_bahan, parent, false)
        return ViewHolder(
            view,
            editClick,
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }


    class ViewHolder(
        view: View,
        val editClick: (BahanDetailResponse) -> Unit,
    ) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: BahanDetailResponse) {

            val imagepath = INTERNET_SERVER + "static/images/" + items.image


            itemView.apply {

                if (items.image.isNotEmpty()) {
                    Glide
                        .with(this)
                        .load(imagepath)
                        .centerCrop()
                        .into(iv_item_bahan)
                } else {
                    Glide
                        .with(this)
                        .load(R.drawable.header)
                        .centerCrop()
                        .into(iv_item_bahan)
                }

                tv_item_bahan_nama.text = items.nama
                tv_item_bahan_spec.text = items.spek

                tv_item_bahan_harga.text =
                    TransformInt().toRupiahString(items.harga) + " / " + items.satuan

                //onClick
                itemView.setOnClickListener { editClick(items) }
            }
        }
    }
}