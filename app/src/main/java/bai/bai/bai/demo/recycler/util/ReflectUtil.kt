package bai.bai.bai.demo.recycler.util

import android.util.Log

import java.lang.reflect.Field

import bai.bai.bai.demo.R

/**
 * Created by Arrow on 2016/10/10.
 */

object ReflectUtil {
    /**
     * 获取图片名称获取图片的资源id的方法 ,调用的时候
     * getResourceByReflect("sdf", R.mipmap::class.java)
     */
    fun getResourceByReflect(imageName: String, mipmap: Class<*>): Int {
//        val mipmaps = mipmap
        var field: Field? = null
        var r_id = 0
        try {
            field = mipmap.getField(imageName)
            field!!.isAccessible = true
            r_id = field.getInt(field.name)
        } catch (e: Exception) {
            Log.e("ERROR", "PICTURE NOT　FOUND！")
        }

        return r_id
    }

    /**
     * 获取图片名称获取图片的drawable id的方法
     * getDrawableByReflect("sdf", R.mipmap::class.java)
     */
    fun getDrawableByReflect(imageName: String,drawable : Class<*>): Int {
//        val drawables = drawable
        var field: Field? = null
        var r_id = 0
        try {
            field = drawable.getField(imageName)
            field!!.isAccessible = true
            r_id = field.getInt(field.name)
        } catch (e: Exception) {
            Log.e("ERROR", "PICTURE NOT　FOUND！")
        }

        return r_id
    }
}
