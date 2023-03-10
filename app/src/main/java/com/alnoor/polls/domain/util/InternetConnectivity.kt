package com.alnoor.polls.domain.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class InternetConnectivity(private val context: Context) {

    private var status: Boolean = false
    private val callbacks = mutableListOf<NetworkStatus>()

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            callbacks.forEach {
                it.onChange(true)
            }
            status = true
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)

        }

        override fun onLost(network: Network) {
            super.onLost(network)
            callbacks.forEach {
                it.onChange(false)
            }
            status = false
        }

    }

    init{
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    fun register(networkStats: NetworkStatus){
        callbacks.add(networkStats)
    }

    fun remove(networkStats: NetworkStatus){
        callbacks.remove(networkStats)
    }

    fun isConnect() = status

    interface NetworkStatus{
        fun onChange(status: Boolean)
    }

}