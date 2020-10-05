package com.laily.globalprint.ui.pemesanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PesananDetailResponse
import com.laily.globalprint.repository.PelangganRepo
import com.laily.globalprint.repository.PesananRepo
import com.laily.globalprint.utils.INTERNET_SERVER

class PesananDetailViewModel : ViewModel() {

    private val _dataPesanan: MutableLiveData<PesananDetailResponse> = MutableLiveData()
    fun getDataPesanan(): MutableLiveData<PesananDetailResponse> {
        return _dataPesanan
    }

    private val _urlTujuan = MutableLiveData<String>()
    val urlTujuan: LiveData<String>
        get() = _urlTujuan

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _isPesananDeleted = MutableLiveData<Boolean>()
    val isPesananDeleted: LiveData<Boolean>
        get() = _isPesananDeleted

    private val _isPesananBerhasilDilunasi = MutableLiveData<Boolean>()
    val isPesananBerhasilDilunasi: LiveData<Boolean>
        get() = _isPesananBerhasilDilunasi

    init {
        _isLoading.value = false
        _isPesananDeleted.value = false
        _messageError.value = ""
        _isPesananBerhasilDilunasi.value = false
    }

    fun mendapatkanPesananDetailDariServer(id: String) {
        _isLoading.value = true
        _messageError.value = ""

        PesananRepo.getPesanan(pesananID = id) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@getPesanan
            }
            response.let {
                _isLoading.value = false
                _dataPesanan.postValue(it)
                _isLoading.value = false
            }
        }
    }

    fun lunasiPesananDariServer(id: String) {
        _isLoading.value = true
        _messageError.value = ""
        _isPesananBerhasilDilunasi.value = false

        PesananRepo.lunasiPesanan(pesananID = id) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@lunasiPesanan
            }
            response.let {
                _dataPesanan.postValue(it)
                _isPesananBerhasilDilunasi.value = true
                _isLoading.value = false
            }
        }
    }

    fun mendapatkanUrlPdfPesanan() {
        _isLoading.value = true
        _messageError.value = ""
        _urlTujuan.value = ""

        PesananRepo.reportsPesananNota(pesananID = _dataPesanan.value?.id?:"") { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@reportsPesananNota
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