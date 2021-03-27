package com.WarwickWestonWright.SmartSpace.Comms

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

class NetUtils(private val iConnectionStatus: IConnectionStatus, context: AppCompatActivity) {

    interface IConnectionStatus {
        fun netStatusSet(isConnected: Boolean)
    }

    private lateinit var netCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var isConnected: Boolean = false

    fun setIsNetworkAvailable() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            isConnected = connectivityManager.activeNetworkInfo != null
            iConnectionStatus.netStatusSet(isConnected)
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            netCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnected = true
                    iConnectionStatus.netStatusSet(isConnected)
                }
                override fun onLost(network: Network) {
                    isConnected = false
                    iConnectionStatus.netStatusSet(isConnected)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(netCallback)
        }
    }
}