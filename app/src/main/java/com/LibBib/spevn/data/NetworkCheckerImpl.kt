package com.LibBib.spevn.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.LibBib.spevn.domain.remoteDB.NetworkChecker
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(
    private val application: Application
) : NetworkChecker {
    override fun isConnected(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false

        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}