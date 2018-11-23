package bai.bai.bai.demo.systembar

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * 沉浸式状态栏
 */
class SystemBarTintManager(activity: Activity) {

    private var mStatusBarTintView: View? = null
    private val DEFAULT_COLOR = Color.GRAY//开关未打开时的默认颜色
    /**
     * 是否打开顶部状态栏开关
     */
    var isStatusBarTintEnabled: Boolean = false
        set(enabled) {
            field = enabled
            if (!enabled) {
                mStatusBarTintView!!.setBackgroundColor(DEFAULT_COLOR)
            }
        }

    init {
        val win = activity.window
        val decorViewGroup = win.decorView as ViewGroup
        if (VERSION.SDK_INT >= 21) {  //android 5.0以上（可去掉状态栏半透明阴影）
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorViewGroup.systemUiVisibility = option
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            win.statusBarColor = Color.TRANSPARENT
        } else if (VERSION.SDK_INT >= 19) {  //android 4.4
            val winParams = win.attributes
            val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            winParams.flags = winParams.flags or bits
            win.attributes = winParams
        }

        setupStatusBarView(activity, decorViewGroup)
        mStatusBarTintView!!.setBackgroundColor(DEFAULT_COLOR)

    }


    /**
     * 设置状态栏为某个颜色
     */
    fun setStatusBarTintColor(color: Int) {
        if (isStatusBarTintEnabled) {
            mStatusBarTintView!!.setBackgroundColor(color)
        }
    }

    /**
     * 设置状态栏为某张图片（直接通过id）
     */
    fun setStatusBarTintResource(res: Int) {
        if (isStatusBarTintEnabled) {
            mStatusBarTintView!!.setBackgroundResource(res)
        }
    }

    /**
     * 设置状态栏为某张图片
     */
    fun setStatusBarTintDrawable(drawable: Drawable) {
        if (isStatusBarTintEnabled) {
            mStatusBarTintView!!.setBackgroundDrawable(drawable)
        }
    }

    /**
     * 设置状态栏透明度
     */
    fun setStatusBarAlpha(alpha: Float) {
        if (isStatusBarTintEnabled) {
            mStatusBarTintView!!.alpha = alpha
        }
    }

    private fun setupStatusBarView(context: Context, decorViewGroup: ViewGroup) {
        mStatusBarTintView = View(context)
        val statusBarHeight = getInternalDimensionSize(context.resources, "status_bar_height")
        val params = android.widget.FrameLayout.LayoutParams(-1, statusBarHeight)
        mStatusBarTintView!!.layoutParams = params
        decorViewGroup.addView(mStatusBarTintView)
    }

    /**
     * 获得状态栏的高度
     */
    private fun getInternalDimensionSize(res: Resources, key: String): Int {
        var result = 0
        val resourceId = res.getIdentifier(key, "dimen", "android")//获得R文件里资源id
        if (resourceId > 0) {
            //getDimension()获得的是绝对尺寸px（得到的是float值，如21.5）
            //getDimensionPixelOffset()是对getDimension()取整(得到int值，21)
            //getDimensionPixelSize()是对getDimension()进行四舍五入（得到int值,22）
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

}

