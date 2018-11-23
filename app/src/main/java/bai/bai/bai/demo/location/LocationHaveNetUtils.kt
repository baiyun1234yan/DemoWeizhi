package bai.bai.bai.demo.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log

//@SuppressLint("MissingPermission")
class LocationHaveNetUtils private constructor(private val mContext: Context) {

    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    private var location: Location? = null
//    private val TAG = "LocationHaveNetUtils ->"
    private val TAG = "LocationHaveNetUtils -> baibai"
    private var logStr: String? = null

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    private var locationListener: LocationListener = object : LocationListener {

        /**
         * 当某个位置提供者的状态发生改变时
         */
        override fun onStatusChanged(provider: String, status: Int, arg2: Bundle) {

        }

        /**
         * 某个设备打开时
         */
        override fun onProviderEnabled(provider: String) {

        }

        /**
         * 某个设备关闭时
         */
        override fun onProviderDisabled(provider: String) {

        }

        /**
         * 手机位置发生变动
         */
        override fun onLocationChanged(location: Location) {
            location.accuracy//精确度
            Log.d(TAG, "监听里 手动位置发生变化 location 是否为空： ${location == null}")
            setLog("监听里 手动位置发生变化 location是否为空： ${location == null}")
            setLocation(location)
        }
    }

    init {
        getLocation()
    }

    private fun getLocation() {
        //1.获取位置管理器
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //2.获取位置提供器，GPS或是NetWork
        val providers = locationManager!!.getProviders(true)//如果是这个获得的是 [passive] 一个（返回的是有效的供应商列表）
//        val providers = locationManager!!.allProviders//得到 [passive, gps, network] 三个（返回的是所有的供应商列表）
        Log.d(TAG, providers.toString())
        setLog(providers.toString())
        locationProvider = when {
            providers.contains(LocationManager.NETWORK_PROVIDER) -> {//如果是网络定位（基站或wifi）//baibai
                Log.d(TAG, "当前是网络定位")
                setLog("当前是网络定位")
                LocationManager.NETWORK_PROVIDER
            }

            providers.contains(LocationManager.GPS_PROVIDER) -> {//如果是GPS定位
                Log.d(TAG, "当前是GPS定位")
                setLog("当前是GPS定位")
                LocationManager.GPS_PROVIDER
            }

            providers.contains(LocationManager.PASSIVE_PROVIDER) -> {//如果是passive定位（即被动方式，是位置更新监测器）
                Log.d(TAG, "当前是passive定位")
                setLog("当前是passive定位")
                LocationManager.PASSIVE_PROVIDER
            }

            else -> {
                Log.d(TAG, "没有可用的位置提供器")
                setLog("没有可用的位置提供器")
                return
            }
        }
        // 需要检查权限,否则编译报错,想抽取成方法都不行,还是会报错。只能这样重复 code 了。
        if (Build.VERSION.SDK_INT >= 23
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        val location = locationManager!!.getLastKnownLocation(locationProvider)
        Log.d(TAG, "工具类getLocation里的location是否为空： ${location == null}")
        setLog("工具类getLocation里的location是否为空： ${location == null}")
        if (location != null) {
            setLocation(location)
        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        locationManager!!.requestLocationUpdates(locationProvider, 0, 0f, locationListener)
    }

    private fun setLocation(location: Location) {
        this.location = location
        val address = "纬度：" + location.latitude + ",  经度：" + location.longitude
        Log.d(TAG, address)
    }

    /**
     * 获取经纬度
     */
    fun showLocation(): Location? {
        return location
    }

    /**
     * 移除定位监听
     */
    fun removeLocationUpdatesListener() {
        // 需要检查权限,否则编译不过
        if (Build.VERSION.SDK_INT >= 23
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (locationManager != null) {
            uniqueInstance = null
            locationManager!!.removeUpdates(locationListener)
        }
        logStr = null
    }

    /**
     * 测试log
     */
    private fun setLog(log: String) {
        logStr += log + "\n"
//        return logStr as String
    }

    fun getLog(): String {
        return logStr as String
    }

    /**
     * 静态
     */
    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var uniqueInstance: LocationHaveNetUtils? = null

        /**
         * 单例
         */
        fun getInstance(context: Context): LocationHaveNetUtils? {
            if (uniqueInstance == null) {
                synchronized(LocationHaveNetUtils::class.java) {
                    if (uniqueInstance == null) {
                        uniqueInstance = LocationHaveNetUtils(context)
                    }
                }
            }
            return uniqueInstance
        }
    }

}
