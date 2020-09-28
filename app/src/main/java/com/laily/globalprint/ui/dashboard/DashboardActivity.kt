package com.laily.globalprint.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laily.globalprint.R
import com.laily.globalprint.ui.bahan.BahanActivity
import com.laily.globalprint.ui.crud.CrudListActivity
import com.laily.globalprint.ui.karyawan.KaryawanActivity
import com.laily.globalprint.ui.pelanggan.PelangganActivity
import com.laily.globalprint.ui.pelanggan.TambahPelangganActivity
import com.laily.globalprint.ui.pemesanan.TambahPesananActivity
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tv_greeting.text = "Hallo ${App.prefs.nameSave}"


        cv_pemesanan.setOnClickListener {
            startActivity(Intent(this, TambahPesananActivity::class.java))
        }

        cv_pelanggan.setOnClickListener {
            startActivity(Intent(this, PelangganActivity::class.java))
        }

        cv_karyawan.setOnClickListener {
            startActivity(Intent(this, KaryawanActivity::class.java))
        }

        cv_penjualan.setOnClickListener {
            startActivity(Intent(this, CrudListActivity::class.java))
        }

        cv_katalog.setOnClickListener {
            startActivity(Intent(this, BahanActivity::class.java))
        }
    }
}