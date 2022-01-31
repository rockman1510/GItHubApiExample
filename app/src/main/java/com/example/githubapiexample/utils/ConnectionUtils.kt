package com.example.githubapiexample.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.app.ActivityCompat

class ConnectionUtils {
    companion object {
        fun checkNetworkEnabled(context: Context?): Boolean {
            if (ActivityCompat.checkSelfPermission(
                    context!!, Manifest.permission.ACCESS_NETWORK_STATE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val nw = connectivityManager.activeNetwork ?: return false
                    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                    return when {
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        //for other device how are able to connect with Ethernet
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        //for check internet over Bluetooth
                        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                        else -> false
                    }
                } else {
                    val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                    return nwInfo.isConnected
                }
            }
            return false
        }
    }
}