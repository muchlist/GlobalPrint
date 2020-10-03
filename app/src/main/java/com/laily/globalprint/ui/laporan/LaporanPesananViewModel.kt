package com.laily.globalprint.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PesananReportsRequest
import com.laily.globalprint.repository.PesananRepo
import com.laily.globalprint.utils.INTERNET_SERVER

class LaporanPesananViewModel : ViewModel() {

    private val _urlTujuan = MutableLiveData<String>()
    val urlTujuan: LiveData<String>
        get() = _urlTujuan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    init {
        _isLoading.value = false
        _messageError.value = ""
        _urlTujuan.value = ""
    }


    fun mendapatkanUrlPdfPesanan(arg: PesananReportsRequest, lunas : String) {
        _isLoading.value = true
        _messageError.value = ""
        _urlTujuan.value = ""

        PesananRepo.reportsPesanan(nama = "", pelangganID = "", lunas = lunas, body = arg) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@reportsPesanan
            }
            response?.let {

                val url = INTERNET_SERVER + "static/pdf/" + it.msg

                _isLoading.value = false
                _urlTujuan.value = url
                _isLoading.value = false
            }
        }
    }
}