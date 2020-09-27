package com.laily.globalprint.ui.bahan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_tambah_bahan.*

class TambahBahanActivity : AppCompatActivity() {

    private lateinit var viewModel: TambahBahanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_bahan)

        viewModel = ViewModelProvider(this).get(TambahBahanViewModel::class.java)
        mengawasiViewModel()

        bt_tambah_bahan_save.setOnClickListener {
            val namaBahan = etf_tambah_bahan_nama.editText?.text.toString()
            val hargaBahan = etf_tambah_bahan_harga.editText?.text.toString().toIntOrNull()
            val satuanBahan = etf_tambah_bahan_satuan.editText?.text.toString()
            val spekBahan = etf_tambah_bahan_spek.editText?.text.toString()
            val dimensi = sw_tambah_bahan.isChecked

            // Jika Form isian ada yang tidak di isi
            if (hargaBahan == null ||
                namaBahan.isEmpty() ||
                satuanBahan.isEmpty() ||
                spekBahan.isEmpty()
            ) {
                tampilkanToast("Inputan tidak valid, harap lengkapi semua form!")
            } else {
                // Jika Form isian lengkap, buat objek untuk diteruskan ke fungsi menambahkanBahan
                // di view model
                val bahanRequest = BahanRequest(
                    nama = namaBahan,
                    harga = hargaBahan,
                    satuan = satuanBahan,
                    spek = spekBahan,
                    punyaDimensi = dimensi
                )
                viewModel.menambahkanBahanKeServer(bahanRequest)
            }
        }
    }


    private fun mengawasiViewModel() {
        viewModel.run {
            isBahanCreatedAndFinish.observe(this@TambahBahanActivity, {
                matikanActivityJikaBahanBerhasilDibuat(it)
            })
            isLoading.observe(this@TambahBahanActivity, { tampilkanLoading(it) })
            messageError.observe(this@TambahBahanActivity, { tampilkanToast(it) })
            messageSuccess.observe(this@TambahBahanActivity, { tampilkanToast(it) })
        }
    }

    private fun matikanActivityJikaBahanBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListBahanHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_tambah_bahan_save.invisible()
        } else {
            bt_tambah_bahan_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}