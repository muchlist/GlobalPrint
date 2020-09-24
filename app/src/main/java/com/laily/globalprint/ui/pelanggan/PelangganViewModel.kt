package com.laily.globalprint.ui.pelanggan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PelangganListResponse
import com.laily.globalprint.repository.PelangganRepo

class PelangganViewModel : ViewModel() {

    //Data untuk RecyclerView
    private val _dataPelanggan: MutableLiveData<PelangganListResponse> = MutableLiveData()
    fun getDataPelanggan(): MutableLiveData<PelangganListResponse> {
        return _dataPelanggan
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _isPelangganDeleted = MutableLiveData<Boolean>()
    val isPelangganDeleted: LiveData<Boolean>
        get() = _isPelangganDeleted

    init {
        _isLoading.value = false
        _isPelangganDeleted.value = false
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

    fun menghapusPelangganDariServer(pelangganID: String) {
        _isLoading.value = true
        _messageError.value = ""
        _isPelangganDeleted.value = false

        PelangganRepo.deletePelanggan(pelangganID = pelangganID) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@deletePelanggan
            }
            response.let {
                _isLoading.value = false
                _isPelangganDeleted.value = true
            }
        }
    }
}