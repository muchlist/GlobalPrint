package com.laily.globalprint.ui.pemesanan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.ui.pelanggan.PelangganViewModel
import kotlinx.android.synthetic.main.activity_tambah_pesanan.*

class TambahPesananActivity : AppCompatActivity() {


    private lateinit var viewModel: TambahPesananViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pesanan)

        clickme.setOnClickListener{
            val dialog = CariBahanDialogFragment()
            dialog.show(supportFragmentManager, "cari_pelanggan")

            viewModel = ViewModelProvider(this).get(TambahPesananViewModel::class.java)
        }
    }
}