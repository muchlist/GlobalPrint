package com.laily.globalprint.ui.pelanggan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.PelangganDetailResponse
import com.laily.globalprint.data.PelangganEditRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_edit_pelanggan.*

class EditPelangganActivity : AppCompatActivity() {

    private lateinit var viewModel: EditPelangganViewModel
    private var dataDariAktifitySebelumnya: PelangganDetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pelanggan)

        dataDariAktifitySebelumnya = intent.getParcelableExtra("data")

        dataDariAktifitySebelumnya?.let {
            mengisiDataKetampilan(it)
        }

        viewModel = ViewModelProvider(this).get(EditPelangganViewModel::class.java)

        mengawasiViewModel()

        iv_back_button.setOnClickListener {
            onBackPressed()
        }

        bt_edit_pelanggan_save.setOnClickListener {

            val dataUntukEditPelanggan = PelangganEditRequest(
                nama = etf_edit_pelanggan_nama.editText?.text.toString(),
                alamat = etf_edit_pelanggan_alamat.editText?.text.toString(),
                email = etf_edit_pelanggan_email.editText?.text.toString(),
                hp = etf_edit_pelanggan_handphone.editText?.text.toString(),
                timestampFilter = dataDariAktifitySebelumnya?.diupdate ?:""
            )
            viewModel.mengeditPelangganKeServer(
                dataDariAktifitySebelumnya?.id ?:"",
                dataUntukEditPelanggan
            )
        }
    }

    private fun mengawasiViewModel() {
        viewModel.isPelangganEditedAndFinish.observe(this, {
            matikanActivityJikaPelangganBerhasilDibuat(it)
        })
        viewModel.isLoading.observe(this, { tampilkanLoading(it) })
        viewModel.messageError.observe(this, { tampilkanToast(it) })
        viewModel.messageSuccess.observe(this, { tampilkanToast(it) })
    }


    private fun mengisiDataKetampilan(data: PelangganDetailResponse) {
        etf_edit_pelanggan_nama.editText?.setText(data.nama)
        etf_edit_pelanggan_alamat.editText?.setText(data.alamat)
        etf_edit_pelanggan_email.editText?.setText(data.email)
        etf_edit_pelanggan_handphone.editText?.setText(data.hp)
    }

    private fun matikanActivityJikaPelangganBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListPelangganHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_edit_pelanggan_save.invisible()
        } else {
            bt_edit_pelanggan_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}