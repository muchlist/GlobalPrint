package com.laily.globalprint.ui.crud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.CrudListResponse
import com.laily.globalprint.data.PelangganListResponse
import com.laily.globalprint.repository.CrudRepo
import com.laily.globalprint.repository.PelangganRepo

class CrudViewModel : ViewModel() {

    //Data untuk RecyclerView
    private val _dataCrud: MutableLiveData<CrudListResponse> = MutableLiveData()
    fun getDataCrud(): MutableLiveData<CrudListResponse> {
        return _dataCrud
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _isCrudDeleted = MutableLiveData<Boolean>()
    val isCrudDeleted: LiveData<Boolean>
        get() = _isCrudDeleted

    init {
        _isLoading.value = false
        _isCrudDeleted.value = false
        _messageError.value = ""
    }


    fun mendapatkanListCrudDariServer(nama: String = "") {
        _isLoading.value = true
        _messageError.value = ""

        CrudRepo.retrieveCrud { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@retrieveCrud
            }
            response.let {
                _isLoading.value = false
                _dataCrud.postValue(it)
                _isLoading.value = false
            }
        }
    }

    fun menghapusCrudDariServer(id: String) {
        _isLoading.value = true
        _messageError.value = ""
        _isCrudDeleted.value = false

        CrudRepo.deleteCrud(crudId = id) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@deleteCrud
            }
            response.let {
                _isLoading.value = false
                _isCrudDeleted.value = true
            }
        }
    }
}