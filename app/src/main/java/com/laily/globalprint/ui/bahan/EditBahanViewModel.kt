package com.laily.globalprint.ui.bahan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.data.BahanEditRequest
import com.laily.globalprint.repository.BahanRepo
import okhttp3.RequestBody

class EditBahanViewModel : ViewModel() {

    private val _bahanDetail = MutableLiveData<BahanDetailResponse>()
    val getBahanDetail: LiveData<BahanDetailResponse>
        get() = _bahanDetail

    fun setBahanDetail(data: BahanDetailResponse){
        _bahanDetail.postValue(data)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isBahanEditdAndFinish = MutableLiveData<Boolean>()
    val isBahanEditdAndFinish: LiveData<Boolean>
        get() = _isBahanEditdAndFinish

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String>
        get() = _messageError

    private val _messageSuccess = MutableLiveData<String>()
    val messageSuccess: LiveData<String>
        get() = _messageSuccess


    init {
        _isLoading.value = false
        _isBahanEditdAndFinish.value = false
        _messageError.value = ""
        _messageSuccess.value = ""
    }

    fun mengeditBahanKeServer(bahanID: String, args: BahanEditRequest) {
        _isLoading.value = true
        _isBahanEditdAndFinish.value = false
        BahanRepo.editBahan(
            bahanID = bahanID,
            args = args
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@editBahan
            }
            response.let {
                _isLoading.value = false
                _messageSuccess.value = "Produk berhasil di update"
                _isBahanEditdAndFinish.value = true
            }
        }
    }

    fun menguploadGambarBahanKeServer(bahanID: String, requestBody: RequestBody) {
        _isLoading.value = true
        BahanRepo.uploadGambarBahan(
            bahanID = bahanID,
            imageFile = requestBody
        ) { response, error ->
            if (error.isNotEmpty()) {
                _isLoading.value = false
                _messageError.value = error
                return@uploadGambarBahan
            }
            response.let {
                _isLoading.value = false
                _bahanDetail.postValue(it)
            }
        }
    }
}