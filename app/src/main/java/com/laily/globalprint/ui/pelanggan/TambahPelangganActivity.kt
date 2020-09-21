package com.laily.globalprint.ui.pelanggan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_tambah_pelanggan.*

class TambahPelangganActivity : AppCompatActivity() {

    private lateinit var viewModel: TambahPelangganViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pelanggan)

        viewModel = ViewModelProvider(this).get(TambahPelangganViewModel::class.java)
        mengawasiViewModel()

        bt_tambah_pelanggan_save.setOnClickListener {
            val idPelanggan = etf_tambah_pelanggan_id.editText?.text.toString()
            val namaPelanggan = etf_tambah_pelanggan_nama.editText?.text.toString()
            val alamatPelanggan = etf_tambah_pelanggan_alamat.editText?.text.toString()
            val emailPelanggan = etf_tambah_pelanggan_email.editText?.text.toString()
            val hpPelanggan = etf_tambah_pelanggan_handphone.editText?.text.toString()

            // Jika Form isian ada yang tidak di isi
            if (idPelanggan.isEmpty() ||
                namaPelanggan.isEmpty() ||
                alamatPelanggan.isEmpty() ||
                emailPelanggan.isEmpty() ||
                hpPelanggan.isEmpty()
            ) {
                tampilkanToast("Inputan tidak valid, harap lengkapi semua form!")
            } else {
                // Jika Form isian lengkap, buat objek untuk diteruskan ke fungsi menambahkanPelanggan
                // di view model
                val pelangganRequest = PelangganRequest(
                    idPelanggan = idPelanggan,
                    nama = namaPelanggan,
                    alamat = alamatPelanggan,
                    email = emailPelanggan,
                    hp = hpPelanggan
                )
                viewModel.menambahkanPelangganKeServer(pelangganRequest)
            }
        }
    }


    private fun mengawasiViewModel() {
        viewModel.run {
            isPelangganCreatedAndFinish.observe(this@TambahPelangganActivity, Observer {
                killActivityIfComputerCreated(it)
            })
            isLoading.observe(this@TambahPelangganActivity, Observer { tampilkanLoading(it) })
            messageError.observe(this@TambahPelangganActivity, Observer { tampilkanToast(it) })
            messageSuccess.observe(this@TambahPelangganActivity, Observer { tampilkanToast(it) })
        }
    }

    private fun killActivityIfComputerCreated(isCreated: Boolean) {
        if (isCreated) {
            App.activityListPelangganHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_tambah_pelanggan_save.invisible()
        } else {
            bt_tambah_pelanggan_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}