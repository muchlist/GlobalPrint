package com.laily.globalprint.ui.karyawan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.UserRequest
import com.laily.globalprint.repository.UserRepo

class TambahKaryawanViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isKaryawanCreatedAndFinish = MutableLiveData<Boolean>()
    val isKaryawanCreatedAndFinish: LiveData<Boolean>
        get() = _isKaryawanCreatedAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isKaryawanCreatedAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun validasiPassword(password: String, passwordVerif: String): Boolean{
        if (password.length < 3){
            return false
        }
        if (password != passwordVerif){
            return false
        }
        return true
    }

    fun menambahkanKaryawanKeServer(args: UserRequest) {
        _isLoading.value = true
        _isKaryawanCreatedAndFinish.value = false
        UserRepo.registerUser(
            arg = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@registerUser
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = it?.msg
                _isKaryawanCreatedAndFinish.value = true
            }
        }
    }
}