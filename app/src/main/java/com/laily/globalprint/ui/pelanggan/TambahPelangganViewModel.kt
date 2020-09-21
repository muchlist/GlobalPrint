package com.laily.globalprint.ui.pelanggan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PelangganRequest
import com.laily.globalprint.repository.PelangganRepo

class TambahPelangganViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isPelangganCreatedAndFinish = MutableLiveData<Boolean>()
    val isPelangganCreatedAndFinish: LiveData<Boolean>
        get() = _isPelangganCreatedAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isPelangganCreatedAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun menambahkanPelangganKeServer(args: PelangganRequest) {
        _isLoading.value = true
        _isPelangganCreatedAndFinish.value = false
        PelangganRepo.createPelanggan(
            args = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@createPelanggan
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = "Menambahkan pelanggan berhasil"
                _isPelangganCreatedAndFinish.value = true

            }
        }
    }
}