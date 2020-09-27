package com.laily.globalprint.ui.bahan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.BahanListResponse
import com.laily.globalprint.repository.BahanRepo

class BahanViewModel : ViewModel() {

    //Data untuk RecyclerView
    private val _dataBahan: MutableLiveData<BahanListResponse> = MutableLiveData()
    fun getDataBahan(): MutableLiveData<BahanListResponse> {
        return _dataBahan
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError


    init {
        _isLoading.value = false
        _messageError.value = ""
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
}