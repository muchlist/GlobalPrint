package com.laily.globalprint.ui.laporan

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.data.PesananReportsRequest
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.toStringInputDate
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_laporan_pesanan.*
import java.text.SimpleDateFormat
import java.util.*

class LaporanPesananActivity : AppCompatActivity() {

    private lateinit var viewModel: LaporanPesananViewModel

    //Date Value
    private lateinit var dateTimeNowCalanderStart: Calendar
    private lateinit var dateTimeNowStart: Date

    private lateinit var dateTimeNowCalanderEnd: Calendar
    private lateinit var dateTimeNowEnd: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_pesanan)

        viewModel = ViewModelProvider(this).get(LaporanPesananViewModel::class.java)

        mengawasiViewModel()

        dateTimeNowCalanderStart = Calendar.getInstance()
        dateTimeNowCalanderEnd = Calendar.getInstance()

        tv_insert_date_start.setOnClickListener {
            menampilkanStartDatePicker()
        }

        tv_insert_date_end.setOnClickListener {
            menampilkanEndDatePicker()
        }

        bt_laporan_piutang.setOnClickListener {
            validasiInputanTanggalDanKirimKeViewModel(lunas = "0")
        }

        bt_laporan_lunas.setOnClickListener {
            validasiInputanTanggalDanKirimKeViewModel(lunas = "1")
        }

        bt_laporan_all.setOnClickListener {
            validasiInputanTanggalDanKirimKeViewModel(lunas = "")
        }
    }

    private fun validasiInputanTanggalDanKirimKeViewModel(lunas: String){
        if (tv_insert_date_start.text.isEmpty() or tv_insert_date_end.text.isEmpty()) {
            menampilkanToastError("Tanggal belum ditentukan!")
            return
        }

        val dataRequest = PesananReportsRequest(
            startDate = dateTimeNowStart.toStringInputDate(),
            endDate = dateTimeNowEnd.toStringInputDate(),
        )
        viewModel.mendapatkanUrlPdfPesanan(arg = dataRequest, lunas = lunas)
    }

    private fun mengawasiViewModel() {

        viewModel.run {
            urlTujuan.observe(this@LaporanPesananActivity, { intentToDownloadPdf(it) })
            isLoading.observe(this@LaporanPesananActivity, { menampilkanLoading(it) })
            messageError.observe(this@LaporanPesananActivity, { menampilkanToastError(it) })
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

    private fun menampilkanStartDatePicker() {
        val format = SimpleDateFormat("dd-MMM", Locale.US)
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                //EXEKUSI DISINI JIKA TANGGAL DIGANTI
                dateTimeNowCalanderStart.set(Calendar.YEAR, year)
                dateTimeNowCalanderStart.set(Calendar.MONTH, month)
                dateTimeNowCalanderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                dateTimeNowCalanderStart.set(Calendar.HOUR_OF_DAY, 0)
                dateTimeNowCalanderStart.set(Calendar.MINUTE, 0)
                dateTimeNowCalanderStart.set(Calendar.SECOND, 1)
                val date = format.format(dateTimeNowCalanderStart.time)
                tv_insert_date_start.text = date
                dateTimeNowStart = dateTimeNowCalanderStart.time
            },
            dateTimeNowCalanderStart.get(Calendar.YEAR),
            dateTimeNowCalanderStart.get(Calendar.MONTH),
            dateTimeNowCalanderStart.get(
                Calendar.DAY_OF_MONTH
            )
        )
        datePicker.show()
    }

    private fun menampilkanEndDatePicker() {
        val format = SimpleDateFormat("dd-MMM", Locale.US)
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                //EXEKUSI DISINI JIKA TANGGAL DIGANTI
                dateTimeNowCalanderEnd.set(Calendar.YEAR, year)
                dateTimeNowCalanderEnd.set(Calendar.MONTH, month)
                dateTimeNowCalanderEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                dateTimeNowCalanderEnd.set(Calendar.HOUR_OF_DAY, 23)
                dateTimeNowCalanderEnd.set(Calendar.MINUTE, 59)
                dateTimeNowCalanderEnd.set(Calendar.SECOND, 59)
                val date = format.format(dateTimeNowCalanderEnd.time)
                tv_insert_date_end.text = date
                dateTimeNowEnd = dateTimeNowCalanderEnd.time
            },
            dateTimeNowCalanderEnd.get(Calendar.YEAR),
            dateTimeNowCalanderEnd.get(Calendar.MONTH),
            dateTimeNowCalanderEnd.get(
                Calendar.DAY_OF_MONTH
            )
        )
        datePicker.show()
    }


}