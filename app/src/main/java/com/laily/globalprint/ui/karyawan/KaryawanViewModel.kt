package com.laily.globalprint.ui.karyawan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.UserListResponse
import com.laily.globalprint.repository.UserRepo

class KaryawanViewModel : ViewModel() {

    //Data untuk RecyclerView
    private val _dataKaryawan: MutableLiveData<UserListResponse> = MutableLiveData()
    fun getDataKaryawan(): MutableLiveData<UserListResponse> {
        return _dataKaryawan
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


    fun mendapatkanUserDariServer(nama: String = "") {
        _isLoading.value = true
        _messageError.value = ""

        UserRepo.retrieveUser(nama = nama) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@retrieveUser
            }
            response.let {
                _isLoading.value = false
                _dataKaryawan.postValue(it)
                _isLoading.value = false
            }
        }
    }
}