package bai.bai.bai.demo.activity

import android.app.Activity
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import bai.bai.bai.demo.utils.IpUtils
//import bai.bai.bai.demo.utils.IpUtils.CONNECT_STATUS
import kotlinx.android.synthetic.main.activity_ip.*

class IpActivity : Activity(), View.OnClickListener {


    val ID = "id"
    val ADDRESS = "address"
    val CONNECT_STATUS = "connect_status"
    var TAG = "baibai"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip)

//        initData()
        initListener()

    }

    private fun initListener() {
        btn_get_ip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_get_ip -> {
                getIp()
                val ipStr = IpUtils.getIPAddress(this)
                Log.d("baibai = 工具类 = ", ipStr)
            }
        }
    }

    private fun getIp() {
        var resolver = contentResolver
        var uri = Uri.parse("content://cn.wiseasy.leopardclaw.ip.provider")
        var cursor = resolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {

            while (!cursor.isAfterLast) {
                var id = cursor.getInt(cursor.getColumnIndex(ID))
                val address = cursor.getString(cursor.getColumnIndex(ADDRESS))
                var connect_status = cursor.getInt(cursor.getColumnIndex(CONNECT_STATUS))
                Log.i(TAG, "getIpAddress:ID =  $id")
                Log.i(TAG, "getIpAddress: address = $address")
                Log.i(TAG, "getIpAddress: connect_status = $connect_status")
                cursor.moveToNext()
                cloud_server_address.text = address

                if (connect_status == 0) {

                    connection_status.text = "====no===="
                } else {

                    connection_status.text = "====yes===="
                }

            }
        }
    }

}
