package work.curioustools.third_party_network.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.annotation.WorkerThread
import work.curioustools.third_party_network.interceptors.InternetAvailabilityInterceptor
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheckUtils{
    fun getCheckerInterceptor(ctx: Context): InternetAvailabilityInterceptor {
        return InternetAvailabilityInterceptor(ctx)
    }
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnectedToInternetProvider(ctx: Context): Boolean {
        val cm = ctx.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        cm?:return false
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {

                val currentNetwork = cm.activeNetwork ?: return false
                val capabilities = cm.getNetworkCapabilities(currentNetwork) ?: return false
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }

            }
            else -> {
                val currentNetworkINFO = cm.activeNetworkInfo ?: return false

                currentNetworkINFO.isConnectedOrConnecting
                        || currentNetworkINFO.type == ConnectivityManager.TYPE_WIFI
                        || currentNetworkINFO.type == ConnectivityManager.TYPE_MOBILE
            }
        }
    }

    @WorkerThread
    fun isReceivingInternetPackets():Boolean{
        //todo move to  core jdk and add core jdk as dependency here
        val dnsPort = 53
        val googleIp = "8.8.8.8"
        val timeOut = 1500
        return kotlin.runCatching {
            val socket = Socket()
            val inetAddress = InetSocketAddress(googleIp,dnsPort)
            socket.connect(inetAddress,timeOut)
            socket.close()
            true
        }.getOrDefault(false)
    }
}