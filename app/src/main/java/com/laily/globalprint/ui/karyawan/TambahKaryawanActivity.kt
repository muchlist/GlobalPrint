package com.laily.globalprint.ui.karyawan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.UserRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.toStringInputDate
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_tambah_karyawan.*
import java.util.*

class TambahKaryawanActivity : AppCompatActivity() {

    private lateinit var viewModel: TambahKaryawanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_karyawan)

        viewModel = ViewModelProvider(this).get(TambahKaryawanViewModel::class.java)
        mengawasiViewModel()

        bt_tambah_karyawan_save.setOnClickListener {
            val idKaryawan = etf_tambah_karyawan_id.editText?.text.toString()
            val namaKaryawan = etf_tambah_karyawan_nama.editText?.text.toString()
            val posisi = etf_tambah_karyawan_posisi.editText?.text.toString()
            val hpKaryawan = etf_tambah_karyawan_handphone.editText?.text.toString()
            val password = etf_tambah_karyawan_password.editText?.text.toString().trim()
            val passwordVerifikasi = etf_tambah_karyawan_password_verif.editText?.text.toString().trim()

            // Jika Form isian ada yang tidak di isi
            if (idKaryawan.isEmpty() ||
                namaKaryawan.isEmpty() ||
                posisi.isEmpty() ||
                hpKaryawan.isEmpty()
            ) {
                tampilkanToast("Inputan tidak valid, harap lengkapi semua form!")
            } else {
                if (!viewModel.validasiPassword(password, passwordVerifikasi)){
                    tampilkanToast("Password tidak sesuai atau tidak valid")
                    etf_tambah_karyawan_password.editText?.setText("")
                    etf_tambah_karyawan_password_verif.editText?.setText("")
                    return@setOnClickListener
                }

                val timeNow = Calendar.getInstance().time
                val dateText = timeNow.toStringInputDate()

                // Jika Form isian lengkap, dan password sama, buat objek untuk diteruskan ke fungsi menambahkanKaryawan
                // di view model
                val karyawanRequest = UserRequest(
                    userId = idKaryawan,
                    name = namaKaryawan,
                    isAdmin = sw_tambah_karyawan.isChecked,
                    isStaff = true,
                    isCustomer = false,
                    address = "",
                    joinDate = dateText,
                    position = posisi,
                    phone = hpKaryawan,
                    password = password,
                )
                viewModel.menambahkanKaryawanKeServer(karyawanRequest)
            }
        }
    }

    private fun mengawasiViewModel() {
        viewModel.run {
            isKaryawanCreatedAndFinish.observe(this@TambahKaryawanActivity, Observer {
                matikanActivityJikaKaryawanBerhasilDibuat(it)
            })
            isLoading.observe(this@TambahKaryawanActivity, Observer { tampilkanLoading(it) })
            messageError.observe(this@TambahKaryawanActivity, Observer { tampilkanToast(it) })
            messageSuccess.observe(this@TambahKaryawanActivity, Observer { tampilkanToast(it) })
        }
    }

    private fun matikanActivityJikaKaryawanBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListKaryawanHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_tambah_karyawan_save.invisible()
        } else {
            bt_tambah_karyawan_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}