package com.laily.globalprint.ui.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.repository.BahanRepo
import com.laily.globalprint.repository.PelangganRepo
import com.laily.globalprint.utils.INTERNET_SERVER

class LaporanViewModel : ViewModel() {

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


    fun mendapatkanUrlPdfBahan() {
        _isLoading.value = true
        _messageError.value = ""
        _urlTujuan.value = ""

        BahanRepo.getReportBahan { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@getReportBahan
            }
            response?.let {

                val url = INTERNET_SERVER + "static/pdf/" + it.msg

                _isLoading.value = false
                _urlTujuan.value = url
                _isLoading.value = false
            }
        }
    }

    fun mendapatkanUrlPdfPelanggan() {
        _isLoading.value = true
        _messageError.value = ""
        _urlTujuan.value = ""

        PelangganRepo.getReportPelanggan() { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@getReportPelanggan
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