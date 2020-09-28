package com.laily.globalprint.ui.bahan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.laily.globalprint.R
import com.laily.globalprint.data.BahanDetailResponse
import com.laily.globalprint.data.BahanEditRequest
import com.laily.globalprint.utils.App
import com.laily.globalprint.utils.INTERNET_SERVER
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_edit_bahan.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditBahanActivity : AppCompatActivity() {

    private lateinit var viewModel: EditBahanViewModel

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE_GALERY = 1001
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bahan)

        viewModel = ViewModelProvider(this).get(EditBahanViewModel::class.java)
        mengawasiViewModel()

        val dataIntent = intent.getParcelableExtra<BahanDetailResponse>("data_bahan")
        dataIntent?.let {
            viewModel.setBahanDetail(dataIntent)
        }

        bt_edit_bahan_upload.setOnClickListener {
            memintaPermissionLaluMembukaGallery()
        }

        bt_edit_bahan_save.setOnClickListener {
            val namaBahan = etf_edit_bahan_nama.editText?.text.toString()
            val hargaBahan = etf_edit_bahan_harga.editText?.text.toString().toIntOrNull()
            val satuanBahan = etf_edit_bahan_satuan.editText?.text.toString()
            val spekBahan = etf_edit_bahan_spek.editText?.text.toString()
            val dimensi = sw_edit_bahan.isChecked

            // Jika Form isian ada yang tidak di isi
            if (hargaBahan == null ||
                namaBahan.isEmpty() ||
                satuanBahan.isEmpty() ||
                spekBahan.isEmpty()
            ) {
                tampilkanToast("Inputan tidak valid, harap lengkapi semua form!")
            } else {
                // Jika Form isian lengkap, buat objek untuk diteruskan ke fungsi menambahkanBahan
                // di view model
                val bahanEditRequest = BahanEditRequest(
                    nama = namaBahan,
                    harga = hargaBahan,
                    satuan = satuanBahan,
                    spek = spekBahan,
                    punyaDimensi = dimensi,
                    timestampFilter = viewModel.getBahanDetail.value?.diupdate ?: ""
                )
                viewModel.mengeditBahanKeServer(
                    bahanID = viewModel.getBahanDetail.value?.id ?: "",
                    args = bahanEditRequest
                )
            }
        }
    }


    private fun mengawasiViewModel() {
        viewModel.run {
            getBahanDetail.observe(this@EditBahanActivity, {
                it?.let {
                    masukkanDataKeTampilan(it)
                }
            })
            isBahanEditdAndFinish.observe(this@EditBahanActivity, {
                matikanActivityJikaBahanBerhasilDibuat(it)
            })
            isLoading.observe(this@EditBahanActivity, { tampilkanLoading(it) })
            messageError.observe(this@EditBahanActivity, { tampilkanToast(it) })
            messageSuccess.observe(this@EditBahanActivity, { tampilkanToast(it) })
        }
    }

    private fun masukkanDataKeTampilan(data: BahanDetailResponse) {
        etf_edit_bahan_nama.editText?.setText(data.nama)
        etf_edit_bahan_harga.editText?.setText(data.harga.toString())
        etf_edit_bahan_satuan.editText?.setText(data.satuan)
        etf_edit_bahan_spek.editText?.setText(data.spek)
        sw_edit_bahan.isChecked = data.punyaDimensi

        if (data.image.isNotEmpty()){

            val imagepath = INTERNET_SERVER + "static/images/" + data.image

            Glide
                .with(this)
                .load(imagepath)
                .centerCrop()
                .into(iv_edit_bahan)
        }
    }

    private fun memintaPermissionLaluMembukaGallery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED
            ) {
                //Permission was not enabled
                val permission = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                //show pop up request permission
                requestPermissions(permission, PERMISSION_CODE_GALERY)
            } else {
                //permission already granted
                pickImageFromGalery()
            }
        } else {
            //system os < Marshmallow
            pickImageFromGalery()
        }
    }

    private fun pickImageFromGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //Called when user allow or deny permission
        when (requestCode) {
            PERMISSION_CODE_GALERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalery()
                } else {
                    tampilkanToast("Penggunaan galery tidak diijinkan")
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val imageUri = data.data

                    imageUri?.let {

                        val inputStream =
                            this.contentResolver.openInputStream(it)

                        val file = File(
                            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "${System.currentTimeMillis()} _image.jpg"
                        )

                        try {
                            FileOutputStream(file).use { outputStream ->
                                inputStream?.copyTo(outputStream)
                            }
                        } catch (e: FileNotFoundException) {
                            tampilkanToast("File tidak ditemukan")
                        } catch (e: IOException) {
                            tampilkanToast("IOException")
                        }

                        scope.launch {
                            val fileCompressed = compressImage(file)
                            val reqFile =
                                RequestBody.create(MediaType.parse("image/*"), fileCompressed)
                            viewModel.menguploadGambarBahanKeServer(
                                bahanID = viewModel.getBahanDetail.value?.id ?:"",
                                requestBody = reqFile
                            )
                        }
                    }
                }
            }
            else -> {
                tampilkanToast("Request code tidak dikenali")
            }
        }
    }


    private suspend fun compressImage(file: File): File {
        return Compressor.compress(this, file)
    }

    private fun matikanActivityJikaBahanBerhasilDibuat(isCreated: Boolean) {
        if (isCreated) {
            App.activityListBahanHarusDiRefresh = true
            finish()
        }
    }

    private fun tampilkanLoading(isLoading: Boolean) {
        if (isLoading) {
            bt_edit_bahan_save.invisible()
        } else {
            bt_edit_bahan_save.visible()
        }
    }

    private fun tampilkanToast(text: String) {
        if (text.isNotEmpty()) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

}