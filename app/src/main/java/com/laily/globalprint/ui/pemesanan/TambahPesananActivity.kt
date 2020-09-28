package com.laily.globalprint.ui.pemesanan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.data.PesananRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.TransformInt
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_tambah_pesanan.*

class TambahPesananActivity : AppCompatActivity() {


    private lateinit var viewModel: TambahPesananViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pesanan)

        viewModel = ViewModelProvider(this).get(TambahPesananViewModel::class.java)

        mengawasiViewModel()

        etf_tambah_pesanan_pelanggan.editText?.setOnClickListener {
            val dialog = CariPelangganDialogFragment()
            dialog.show(supportFragmentManager, "cari_pelanggan")
        }

        etf_tambah_pesanan_bahan.editText?.setOnClickListener {
            val dialog = CariBahanDialogFragment()
            dialog.show(supportFragmentManager, "cari_bahan")
        }

        sw_pesanan_lunas.setOnClickListener {
            if (sw_pesanan_lunas.isChecked) {
                etf_tambah_pesanan_uang_muka.editText?.setText("")
                etf_tambah_pesanan_uang_muka.invisible()
            } else {
                etf_tambah_pesanan_uang_muka.visible()
            }
        }

        bt_tambah_pesanan_save.setOnLongClickListener {
            mengambilFormLaluMenyimpanKeServer()
            false
        }
        bt_tambah_pesanan_save.setOnClickListener {
            menampilkanToast("Tahan tombol untuk menyimpan!")
        }

        bt_tambah_pesanan_kalkulasi.setOnClickListener {
            val qty = etf_tambah_pesanan_qty.editText?.text.toString().toIntOrNull() ?: -1
            val uangMuka = etf_tambah_pesanan_uang_muka.editText?.text.toString().toIntOrNull() ?: 0
            val ukuranX = etf_tambah_pesanan_x.editText?.text.toString().toIntOrNull() ?: 1
            val ukuranY = etf_tambah_pesanan_y.editText?.text.toString().toIntOrNull() ?: 1
            val harga = viewModel.getDataBahanTerpilih().value?.harga ?: -1

            if (qty < 0 || uangMuka < 0 || harga < 0){
                menampilkanToast("Harap lengkapi form terlebih dahulu")
                return@setOnClickListener
            }

            val kalkulasi = (harga * ukuranX * ukuranY * qty) - uangMuka
            if (sw_pesanan_lunas.isChecked){
                tv_tambah_pesanan_sisa_bayar.setText("Sisa bayar :\n${TransformInt().toRupiahString(0)}")
            }else{
                tv_tambah_pesanan_sisa_bayar.setText("Sisa bayar :\n${TransformInt().toRupiahString(kalkulasi)}")
            }


        }

    }

    private fun mengawasiViewModel() {

        viewModel.run {
            getDataBahanTerpilih().observe(this@TambahPesananActivity, {
                etf_tambah_pesanan_bahan.editText?.setText(it.nama)
                munculkanDimensiJikaBahanBerdimensi(it)
            })

            getDataPelangganTerpilih().observe(this@TambahPesananActivity, {
                etf_tambah_pesanan_pelanggan.editText?.setText(it.nama)
            })

            isLoading.observe(this@TambahPesananActivity, { menampilkanLoading(it) })
            messageError.observe(this@TambahPesananActivity, { menampilkanToast(it) })
            isPesananCreated.observe(this@TambahPesananActivity, {
                matikanActivityJikaPesananBerhasilDibuat(it)

            })
        }
    }

    private fun mengambilFormLaluMenyimpanKeServer() {
        var ukuranX = 1
        var ukuranY = 1
        if (viewModel.getDataBahanTerpilih().value?.punyaDimensi == true) {
            ukuranX = etf_tambah_pesanan_x.editText?.text.toString().toIntOrNull() ?: -1
            ukuranY = etf_tambah_pesanan_y.editText?.text.toString().toIntOrNull() ?: -1
        }

        val body = PesananRequest(
            apakahLunas = sw_pesanan_lunas.isChecked,
            finishing = etf_tambah_pesanan_finishing.editText?.text.toString(),
            idBahan = viewModel.getDataBahanTerpilih().value?.id ?: "",
            idPelanggan = viewModel.getDataPelangganTerpilih().value?.idPelanggan ?: "",
            namaFile = etf_tambah_pesanan_nama_file.editText?.text.toString(),
            namaPesanan = etf_tambah_pesanan_judul.editText?.text.toString(),
            qty = etf_tambah_pesanan_qty.editText?.text.toString().toIntOrNull() ?: -1,
            uangMuka = etf_tambah_pesanan_uang_muka.editText?.text.toString().toIntOrNull() ?: 0,
            ukuranX = ukuranX,
            ukuranY = ukuranY
        )
        if (body.validasi()) {
            //inputan valid
            viewModel.menambahkanPesananKeServer(args = body)
        } else {
            //inputan tidak valid
            menampilkanToast("Harap lengkapi formulir")
        }
    }

    private fun munculkanDimensiJikaBahanBerdimensi(bahan: BahanDetailResponse) {
        if (bahan.punyaDimensi) {
            container_xy.visible()
        } else {
            etf_tambah_pesanan_x.editText?.setText("")
            etf_tambah_pesanan_y.editText?.setText("")
            container_xy.invisible()
        }
    }

    private fun menampilkanLoading(isLoading: Boolean) {
    }

    private fun menampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }


    private fun matikanActivityJikaPesananBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListPesananHarusDiRefresh = true
            finish()
        }
    }

}