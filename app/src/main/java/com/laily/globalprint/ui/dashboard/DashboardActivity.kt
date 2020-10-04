package com.laily.globalprint.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.laily.globalprint.R
import com.laily.globalprint.ui.bahan.BahanActivity
import com.laily.globalprint.ui.crud.CrudListActivity
import com.laily.globalprint.ui.karyawan.KaryawanActivity
import com.laily.globalprint.ui.laporan.LaporanActivity
import com.laily.globalprint.ui.pelanggan.PelangganActivity
import com.laily.globalprint.ui.pemesanan.PesananActivity
import com.laily.globalprint.ui.pemesanan.TambahPesananActivity
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlin.system.exitProcess

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
            startActivity(Intent(this, PesananActivity::class.java))
        }

        cv_katalog.setOnClickListener {
            startActivity(Intent(this, BahanActivity::class.java))
        }

        cv_laporan.setOnClickListener {
            startActivity(Intent(this, LaporanActivity::class.java))
        }

        cv_crud.setOnClickListener {
            startActivity(Intent(this, CrudListActivity::class.java))
        }

        iv_logout.setOnClickListener {
            memunculkanDialogLogout()
        }
    }

    private fun memunculkanDialogLogout() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Konfirmasi")
        builder.setMessage("Yakin ingin logout?")

        builder.setPositiveButton("Ya") { _, _ ->
            App.prefs.authTokenSave = ""
            finish()
        }
        builder.setNeutralButton("Batal") { _, _ ->

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}