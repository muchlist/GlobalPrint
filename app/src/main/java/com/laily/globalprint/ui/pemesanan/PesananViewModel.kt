package com.laily.globalprint.ui.pemesanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.PesananListResponse
import com.laily.globalprint.repository.PesananRepo

class PesananViewModel  : ViewModel() {

    //Data untuk RecyclerView
    private val _dataPesanan: MutableLiveData<PesananListResponse> = MutableLiveData()
    fun getDataPesanan(): MutableLiveData<PesananListResponse> {
        return _dataPesanan
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _isPesananDeleted = MutableLiveData<Boolean>()
    val isPesananDeleted: LiveData<Boolean>
        get() = _isPesananDeleted

    init {
        _isLoading.value = false
        _isPesananDeleted.value = false
        _messageError.value = ""
    }


    fun mendapatkanPesananDariServer(nama: String) {
        _isLoading.value = true
        _messageError.value = ""

        PesananRepo.retrievePesanan(nama = nama) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@retrievePesanan
            }
            response.let {
                _isLoading.value = false
                _dataPesanan.postValue(it)
                _isLoading.value = false
            }
        }
    }
}