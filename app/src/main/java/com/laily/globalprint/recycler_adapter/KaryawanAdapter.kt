package com.laily.globalprint.recycler_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laily.globalprint.R
import com.laily.globalprint.data.UserListResponse
import com.laily.globalprint.utils.toDate
import com.laily.globalprint.utils.toStringDateMonthYear
import kotlinx.android.synthetic.main.item_karyawan.view.*

class KaryawanAdapter(
    private val context: Context?,
    private val itemList: List<UserListResponse.User>,
) : RecyclerView.Adapter<KaryawanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_karyawan, parent, false)
        return ViewHolder(
            view,
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList[position])
    }


    class ViewHolder(
        view: View,
    ) :
        RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindItem(items: UserListResponse.User) {

            itemView.apply {
                tv_item_id_karyawan.text = items.userId
                tv_item_nama_karyawan.text = items.name
                tv_item_no_hp_karyawan.text = items.phone
                tv_item_role_karyawan.text = items.position
                tv_item_masa_kerja_karyawan.text =
                    "Tanggal bergabung : " + items.joinDate.toDate().toStringDateMonthYear()

            }
        }
    }
}