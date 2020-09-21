package com.laily.globalprint.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laily.globalprint.R
import com.laily.globalprint.ui.karyawan.KaryawanActivity
import com.laily.globalprint.ui.pelanggan.PelangganActivity
import com.laily.globalprint.utils.App
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tv_greeting.text = "Hallo ${App.prefs.nameSave}"
        cv_pelanggan.setOnClickListener {
            startActivity(Intent(this, PelangganActivity::class.java))
        }

        cv_karyawan.setOnClickListener {
            startActivity(Intent(this, KaryawanActivity::class.java))
        }
    }
}