package com.laily.globalprint.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.LoginRequest
import com.laily.globalprint.data.LoginResponse
import com.laily.globalprint.repository.LoginRepo
import com.laily.globalprint.utils.App

class LoginViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean>
        get() = _isLoginSuccess

    private val _isError = MutableLiveData<String>()
    val isError: LiveData<String>
        get() = _isError

    init {
        _isLoading.value = false
        _isLoginSuccess.value = false
        _isError.value = ""
    }

    fun melakukanLoginKeServer(data: LoginRequest) {
        _isLoading.value = true
        _isError.value = ""
        LoginRepo.melakukanLogin(data) { response, error ->
            if (error.isNotEmpty()) {
                // jika terjadi error pada login
                _isLoading.value = false
                _isError.value = error
                return@melakukanLogin
            }

            response?.let {
                //jika resquest ke server berhasil
                _isLoading.value = false
                menyimpanDataLoginKeSharedPref(it)
                _isLoginSuccess.value = true
            }
        }
    }

    private fun menyimpanDataLoginKeSharedPref(data: LoginResponse) {
        val pref = App.prefs
        pref.authTokenSave = "Bearer "+ data.accessToken
        pref.nameSave = data.name
        pref.isAdmin = data.isAdmin
        pref.isStaff = data.isStaff
        pref.isCustomer = data.isCustomer
    }

}