package bai.bai.bai.demo.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import bai.bai.bai.demo.location.LocationUtils
import kotlinx.android.synthetic.main.activity_location.*
import android.widget.Toast
import bai.bai.bai.demo.location.LocationHaveNetUtils

/**
 * 定位
 */
class LocationActivity : Activity(), View.OnClickListener {


    private var LOCATION_CODE = 111
    private var mLocationUtils: LocationUtils? = null
    private var mLocationHaveNetUtils: LocationHaveNetUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        initListener()
    }

    private fun initListener() {
        tv_log_have_net.movementMethod = ScrollingMovementMethod()
        btn_location_no_net.setOnClickListener(this)
        btn_location_have_net.setOnClickListener(this)
        btn_remove.setOnClickListener(this)
        btn_permission.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_location_no_net -> {
                mLocationUtils = LocationUtils.getInstance(this)
                val location = mLocationUtils!!.showLocation()
                tv_log_no_net.text = mLocationUtils!!.getLog()
                if (location != null) {
                    val address = "纬度：" + location.latitude + ",  经度：" + location.longitude
                    tv_info_no_net.text = address
                } else {
                    tv_info_no_net.text = "location为空"
                }
            }

            R.id.btn_location_have_net -> {
                mLocationHaveNetUtils = LocationHaveNetUtils.getInstance(this)
                val location = mLocationHaveNetUtils!!.showLocation()
                tv_log_have_net.text = mLocationHaveNetUtils!!.getLog()
                if (location != null) {
                    val address = "纬度：" + location.latitude + ",  经度：" + location.longitude
                    tv_info_have_net.text = address
                } else {
                    tv_info_have_net.text = "location为空"
                }
            }

            R.id.btn_remove -> {
                if (mLocationUtils != null) mLocationUtils!!.removeLocationUpdatesListener()
                if (mLocationHaveNetUtils != null) mLocationHaveNetUtils!!.removeLocationUpdatesListener()
                tv_log_have_net.text = ""
                tv_log_no_net.text = ""
                tv_info_have_net.text = ""
                tv_info_no_net.text = ""
            }

            R.id.btn_permission -> {
                checkPermission()
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_CODE -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
                    Log.d("baibai", "onRequestPermissionsResult === 弹窗已同意")

                } else {
                    Log.d("baibai", "onRequestPermissionsResult === 弹窗未同意")
                    // 权限被用户拒绝了。
                    //若是点击了拒绝和不再提醒
                    //关于shouldShowRequestPermissionRationale
                    // 1、当用户第一次被询问是否同意授权的时候，返回false
                    // 2、当之前用户被询问是否授权，点击了false,并且点击了不在询问（第一次询问不会出现“不再询问”的选项），之后便会返回false
                    // 3、当用户被关闭了app的权限，该app不允许授权的时候，返回false
                    // 4、当用户上一次不同意授权，没有点击“不再询问”的时候，下一次返回true
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //提示用户前往设置界面自己打开权限
                        Toast.makeText(this, "请前往设置界面打开权限", Toast.LENGTH_SHORT).show()
                        return
                    }

                }
            }
        }
    }

    /**
     * 获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
     */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //没有开启权限，请求权限
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_CODE)
            Log.d("baibai", "permission -- 权限未开启")
        } else {
            Log.d("baibai", "permission -- 权限已开启")
        }

    }

}
