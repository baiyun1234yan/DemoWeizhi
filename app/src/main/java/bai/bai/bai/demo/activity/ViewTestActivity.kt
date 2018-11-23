package bai.bai.bai.demo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import bai.bai.bai.demo.R
import bai.bai.bai.demo.recycler.util.ReflectUtil
import bai.bai.bai.demo.utils.ReflectUtils.getResourceByReflect

/**
 * 测试
 */
class ViewTestActivity : AppCompatActivity() {

    var lastX: Float = 0F
    var lastY: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)

//        getResourceByReflect("sdf", R.mipmap::class.java)
//        ReflectUtil.getResourceByReflect("sdf", R.mipmap::class.java)

    }


}
