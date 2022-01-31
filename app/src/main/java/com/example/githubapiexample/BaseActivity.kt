package com.example.githubapiexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapiexample.utils.CustomDialog

open class BaseActivity : AppCompatActivity() {
    private var loadingDialog: CustomDialog? = null

    fun getLoadingDialog(): CustomDialog? = loadingDialog!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoadingDialog()
    }

    private fun initLoadingDialog() {
        loadingDialog = CustomDialog.Builder(this).setLoadingContent(true)
            .setCancelable(false).setTimeOut(Constants.LOADING_DIALOG_TIMEOUT)
            .setMessage(getString(R.string.please_wait)).build()
    }
}