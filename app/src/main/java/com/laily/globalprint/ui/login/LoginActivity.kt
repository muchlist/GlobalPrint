package com.laily.globalprint.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.laily.globalprint.R
import com.laily.globalprint.ui.dashboard.DashboardActivity
import com.laily.globalprint.data.LoginRequest
import com.laily.globalprint.utils.invisible
import com.laily.globalprint.utils.visible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        menyembunyikanBarNotifikasi()
        mengawasiViewModel()

        btn_login_login.setOnClickListener {
            //Klik pada tombol login

            val userID = edt_username_login.editText?.text?.toString()?.trim() ?: ""
            val password = edt_password_login.editText?.text?.toString() ?: ""

            //mengecek apakah user ID dan Password kosong
            if (userID.isEmpty() || password.isEmpty()) {
                //jika kosong tampilkan peringatan
                Toast.makeText(
                    this,
                    "Username dan Password tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //jika userID dan Password isi panggil fungsi melakukanLoginKeServer pada viewModel
                val loginDataDikirim = LoginRequest(userId = userID, password = password)
                viewModel.melakukanLoginKeServer(loginDataDikirim)
            }
        }

        menyembunyikanKeyboardSaatPertamakali()
    }


    private fun menyembunyikanKeyboardSaatPertamakali() {
        edt_username_login.editText?.clearFocus()
        edt_password_login.editText?.clearFocus()
    }

    private fun mengawasiViewModel() {

        viewModel.run {
            isLoginSuccess.observe(this@LoginActivity, { berpindahKeHalamanDashboard(it) })
            isLoading.observe(this@LoginActivity, { tampilkanProgressBar(it) })
            isError.observe(this@LoginActivity, {
                if (it.isNotEmpty()){
                Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun menyembunyikanBarNotifikasi() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun berpindahKeHalamanDashboard(ok: Boolean) {
        if (ok) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun tampilkanProgressBar(isLoading: Boolean) {
        if (isLoading) {
            pb_login.visible()
            btn_login_login.invisible()
        } else {
            pb_login.invisible()
            btn_login_login.visible()
        }
    }

}