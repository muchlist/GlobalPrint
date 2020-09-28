package com.laily.globalprint.ui.pemesanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.*
import com.laily.globalprint.repository.BahanRepo
import com.laily.globalprint.repository.PelangganRepo
import com.laily.globalprint.repository.PesananRepo

class TambahPesananViewModel : ViewModel() {

    //Data untuk RecyclerView
    private val _dataPelanggan: MutableLiveData<PelangganListResponse> = MutableLiveData()
    fun getDataPelanggan(): MutableLiveData<PelangganListResponse> {
        return _dataPelanggan
    }

    private val _dataPelangganTerpilih: MutableLiveData<PelangganDetailResponse> = MutableLiveData()
    fun getDataPelangganTerpilih(): MutableLiveData<PelangganDetailResponse> {
        return _dataPelangganTerpilih
    }

    //Data untuk RecyclerView
    private val _dataBahan: MutableLiveData<BahanListResponse> = MutableLiveData()
    fun getDataBahan(): MutableLiveData<BahanListResponse> {
        return _dataBahan
    }

    private val _dataBahanTerpilih: MutableLiveData<BahanDetailResponse> = MutableLiveData()
    fun getDataBahanTerpilih(): MutableLiveData<BahanDetailResponse> {
        return _dataBahanTerpilih
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _isPesananCreated = MutableLiveData<Boolean>()
    val isPesananCreated: LiveData<Boolean>
        get() = _isPesananCreated

    init {
        _isLoading.value = false
        _messageError.value = ""
    }

    fun mendapatkanPelangganDariServer(nama: String) {
        _isLoading.value = true
        _messageError.value = ""

        PelangganRepo.retrievePelanggan(nama = nama) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@retrievePelanggan
            }
            response.let {
                _isLoading.value = false
                _dataPelanggan.postValue(it)
                _isLoading.value = false
            }
        }
    }

    fun pilihPelanggan(data: PelangganDetailResponse) {
        _dataPelangganTerpilih.postValue(data)
    }

    fun mendapatkanBahanDariServer(nama: String) {
        _isLoading.value = true
        _messageError.value = ""

        BahanRepo.retrieveBahan(nama = nama) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@retrieveBahan
            }
            response.let {
                _isLoading.value = false
                _dataBahan.postValue(it)
                _isLoading.value = false
            }
        }
    }

    fun pilihBahan(data: BahanDetailResponse) {
        _dataBahanTerpilih.postValue(data)
    }

    fun menambahkanPesananKeServer(args: PesananRequest) {
        _isLoading.value = true
        _messageError.value = ""
        _isPesananCreated.value = false
        PesananRepo.createPesanan(args = args) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@createPesanan
            }
            response?.let {
                _isLoading.value = false
                _messageError.value = it.msg
                _isPesananCreated.value = true
            }
        }
    }


}