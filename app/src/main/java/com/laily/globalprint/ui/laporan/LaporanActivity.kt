package com.laily.globalprint.ui.laporan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_laporan.*

class LaporanActivity : AppCompatActivity() {

    private lateinit var viewModel: LaporanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan)

        viewModel = ViewModelProvider(this).get(LaporanViewModel::class.java)

        mengawasiViewModel()

        cv_export_bahan.setOnClickListener {
            viewModel.mendapatkanUrlPdfBahan()
        }
        cv_export_pelanggan.setOnClickListener {
            viewModel.mendapatkanUrlPdfPelanggan()
        }


    }

    private fun mengawasiViewModel() {

        viewModel.run {
            urlTujuan.observe(this@LaporanActivity, { intentToDownloadPdf(it) })
            isLoading.observe(this@LaporanActivity, { menampilkanLoading(it) })
            messageError.observe(this@LaporanActivity, { menampilkanToastError(it) })
        }
    }

    private fun intentToDownloadPdf(url: String) {
        if (url.isNotEmpty()) {
            val uri: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun menampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            pb_laporan.visible()
        } else {
            pb_laporan.invisible()
        }

    }

    private fun menampilkanToastError(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}