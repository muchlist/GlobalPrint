package com.laily.globalprint.ui.bahan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.BahanRequest
import com.laily.globalprint.repository.BahanRepo

class TambahBahanViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isBahanCreatedAndFinish = MutableLiveData<Boolean>()
    val isBahanCreatedAndFinish: LiveData<Boolean>
        get() = _isBahanCreatedAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isBahanCreatedAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun menambahkanBahanKeServer(args: BahanRequest) {
        _isLoading.value = true
        _isBahanCreatedAndFinish.value = false
        BahanRepo.createBahan(
            args = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@createBahan
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = it?.msg
                _isBahanCreatedAndFinish.value = true
            }
        }
    }
}