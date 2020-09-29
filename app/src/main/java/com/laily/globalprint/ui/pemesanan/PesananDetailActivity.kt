package com.laily.globalprint.ui.pemesanan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.PesananDetailResponse
import com.laily.globalprint.utils.*
import kotlinx.android.synthetic.main.activity_pesanan_detail.*

class PesananDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PesananDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_detail)

        val dataintent = intent.getStringExtra("pesanan_id") ?: ""

        viewModel = ViewModelProvider(this).get(PesananDetailViewModel::class.java)
        mengawasiViewModel()
        viewModel.mendapatkanPesananDetailDariServer(dataintent)

        bt_pesanan_detail_lunas.setOnClickListener {
            menampilkanToastError("Tahan tombol untuk melunaskan")
        }
        bt_pesanan_detail_lunas.setOnLongClickListener {
            viewModel.lunasiPesananDariServer(dataintent)
            false
        }

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataPesanan().observe(this@PesananDetailActivity, {
                it?.let {
                    setDataKedalamView(it)
                }
            })
            isLoading.observe(this@PesananDetailActivity, { menampilkanLoading(it) })
            messageError.observe(this@PesananDetailActivity, { menampilkanToastError(it) })
            isPesananBerhasilDilunasi.observe(this@PesananDetailActivity,{
                if (it){
                    App.activityListPesananHarusDiRefresh = true
                }
            })
        }
    }

    private fun setDataKedalamView(it: PesananDetailResponse) {

        if (it.biaya.apakahLunas) {
            bt_pesanan_detail_lunas.invisible()
            iv_lunas.visible()
        } else {
            bt_pesanan_detail_lunas.visible()
            iv_lunas.invisible()
        }

        tv_pesanan_detail_no_transaksi.text = it.id
        tv_pesanan_detail_nama.text = it.pelanggan.namaPelanggan
        tv_pesanan_detail_file.text = it.namaFile
        tv_pesanan_detail_pesanan.text = it.namaPesanan
        tv_pesanan_detail_ukuran.text =
            it.bahan.ukuranX.toString() + " X " + it.bahan.ukuranY.toString() + " " + it.bahan.satuanBahan
        tv_pesanan_detail_qty.text = it.bahan.qty.toString()
        tv_pesanan_detail_bahan.text = it.bahan.namaBahan
        tv_pesanan_detail_harga.text = TransformInt().toRupiahString(it.bahan.hargaBahan)
        tv_pesanan_detail_total.text = TransformInt().toRupiahString(it.biaya.totalBayar)
        tv_pesanan_detail_sisa_bayar.text = TransformInt().toRupiahString(it.biaya.sisaBayar)
        tv_pesanan_detail_status_lunas.text =
            if (it.biaya.apakahLunas) {
                "Lunas"
            } else {
                "Belum lunas"
            }
        tv_pesanan_detail_tanggal.text = it.dibuat.toDate().toStringDateMonthYear()
        tv_pesanan_detail_tanggal_lunas.text =
            if (it.biaya.apakahLunas) {
                it.diupdate.toDate().toStringDateMonthYear()
            } else {
                "---"
            }
        tv_pesanan_detail_petugas.text = it.diupdateOleh

    }

    private fun menampilkanLoading(isLoading: Boolean) {
        if (isLoading){
            progressBar.visible()
        } else {
            progressBar.invisible()
        }
    }


    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}