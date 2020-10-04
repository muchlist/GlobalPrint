package com.laily.globalprint.ui.crud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.data.CrudRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_tambah_crud.*


class TambahCrudActivity : AppCompatActivity() {

    private lateinit var viewModel: TambahCrudViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_crud)

        //mengambil data dari intent extra
        val judulIntent = intent.getStringExtra("form_crud") ?: "Tambah Crud"
        val mode = intent.getIntExtra("mode_crud", 0)  //NOL berarti tambah , 1 berarti edit
        val idCrud = intent.getStringExtra("id_crud") ?: ""
        val dataForm = intent.getParcelableExtra<CrudListResponse.Crud>("crud_data")

        viewModel = ViewModelProvider(this).get(TambahCrudViewModel::class.java)
        mengawasiViewModel()

        if (mode == 1){
            dataForm?.let {
                etf_tambah_crud_nama.editText?.setText(it.nama)
                etf_tambah_crud_alamat.editText?.setText(it.alamat)
                etf_tambah_crud_keterangan.editText?.setText(it.keterangan)
            }
        }

        title_form_crud.text = judulIntent

        bt_tambah_crud_save.setOnClickListener {
            val namaCrud = etf_tambah_crud_nama.editText?.text.toString()
            val alamatCrud = etf_tambah_crud_alamat.editText?.text.toString()
            val keteranganCrud = etf_tambah_crud_keterangan.editText?.text.toString()

            // Jika Form isian ada yang tidak di isi
            if (namaCrud.isEmpty() ||
                alamatCrud.isEmpty() ||
                keteranganCrud.isEmpty()
            ) {
                tampilkanToast("Inputan tidak valid, harap lengkapi semua form!")
            } else {
                // Jika Form isian lengkap, buat objek untuk diteruskan ke fungsi menambahkanCrud
                // di view model
                val crudRequest = CrudRequest(
                    nama = namaCrud,
                    alamat = alamatCrud,
                    keterangan = keteranganCrud
                )
                if (mode == 0) {
                    //menambahkan crud
                    viewModel.menambahkanCrudKeServer(crudRequest)
                } else {
                    // mengedit crud
                    viewModel.mengeditCrudKeServer(idCrud, crudRequest)
                }

            }
        }

        iv_back_button.setOnClickListener {
            onBackPressed()
        }
    }


    private fun mengawasiViewModel() {
        viewModel.run {
            isCrudCreatedAndFinish.observe(this@TambahCrudActivity, {
                matikanActivityJikaCrudBerhasilDibuat(it)
            })
            isLoading.observe(this@TambahCrudActivity, { tampilkanLoading(it) })
            messageError.observe(this@TambahCrudActivity, { tampilkanToast(it) })
            messageSuccess.observe(this@TambahCrudActivity, { tampilkanToast(it) })
        }
    }

    private fun matikanActivityJikaCrudBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListCrudHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_tambah_crud_save.invisible()
        } else {
            bt_tambah_crud_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}