package com.laily.globalprint.ui.crud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.CrudRequest
import com.laily.globalprint.repository.CrudRepo

class TambahCrudViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isCrudCreatedAndFinish = MutableLiveData<Boolean>()
    val isCrudCreatedAndFinish: LiveData<Boolean>
        get() = _isCrudCreatedAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isCrudCreatedAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun menambahkanCrudKeServer(args: CrudRequest) {
        _isLoading.value = true
        _isCrudCreatedAndFinish.value = false
        CrudRepo.createCrud(
            args = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@createCrud
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = it?.msg
                _isCrudCreatedAndFinish.value = true
            }
        }
    }

    fun mengeditCrudKeServer(id: String, args: CrudRequest) {
        _isLoading.value = true
        _isCrudCreatedAndFinish.value = false
        CrudRepo.editCrud(
            args = args,
            crudId = id
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@editCrud
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = "berhasil mengedit"
                _isCrudCreatedAndFinish.value = true
            }
        }
    }
}