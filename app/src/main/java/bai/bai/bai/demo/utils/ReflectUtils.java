package bai.bai.bai.demo.utils;

import android.util.Log;

import java.lang.reflect.Field;

import bai.bai.bai.demo.R;

/**
 * Created by Arrow on 2016/10/10.
 */

public class ReflectUtils {
    /**
     * 获取图片名称获取图片的资源id的方法
     * @return
     */
    public static int getResourceByReflect(String imageName, Class mipmap) {
//        Class mipmap = R.mipmap.class;
//        Class mipmaps = mipmap;
        Field field = null;
        int r_id = 0;
        try {
            field = mipmap.getField(imageName);
            field.setAccessible(true);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }

    /**
     * 获取图片名称获取图片的drawable id的方法
     */
    public static int getDrawableByReflect(String imageName, Class drawable) {
//        Class drawable = R.drawable.class;
        Field field = null;
        int r_id = 0;
        try {
            field = drawable.getField(imageName);
            field.setAccessible(true);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
}
