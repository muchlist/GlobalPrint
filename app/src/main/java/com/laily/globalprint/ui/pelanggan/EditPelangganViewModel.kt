package com.laily.globalprint.ui.pelanggan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PelangganEditRequest
import com.laily.globalprint.repository.PelangganRepo

class EditPelangganViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isPelangganEditedAndFinish = MutableLiveData<Boolean>()
    val isPelangganEditedAndFinish: LiveData<Boolean>
        get() = _isPelangganEditedAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isPelangganEditedAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun mengeditPelangganKeServer(pelangganID: String, args: PelangganEditRequest) {
        _isLoading.value = true
        _isPelangganEditedAndFinish.value = false
        PelangganRepo.editPelanggan(
            pelangganID = pelangganID, args = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@editPelanggan
            }
            response.let {
                _isLoading.value = false
                _isPelangganEditedAndFinish.value = true
            }
        }
    }
}