package bai.bai.bai.demo.systembar


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

class SystemBarManager @TargetApi(19)
constructor(activity: Activity) {
    val config: SystemBarManager.SystemBarConfig
    private var mStatusBarAvailable: Boolean = false
    private var mNavBarAvailable: Boolean = false
    //baibaibai   0:8
    var isStatusBarTintEnabled: Boolean = false
        set(enabled) {
            field = enabled
            if (this.mStatusBarAvailable) {
                this.mStatusBarTintView!!.visibility = if (enabled) View.VISIBLE else View.GONE
            }

        }
    var isNavBarTintEnabled: Boolean = false
        private set
    private var mStatusBarTintView: View? = null
    private var mNavBarTintView: View? = null

    init {
        val win = activity.window
        val decorViewGroup = win.decorView as ViewGroup
        if (VERSION.SDK_INT >= 19) {
            val attrs = intArrayOf(16843759, 16843760)
            val a = activity.obtainStyledAttributes(attrs)

            try {
                this.mStatusBarAvailable = a.getBoolean(0, false)
                this.mNavBarAvailable = a.getBoolean(0, false)
            } finally {
                a.recycle()
            }

            val winParams = win.attributes
            var bits = 67108864
            if (winParams.flags and bits != 0) {
                this.mStatusBarAvailable = true
            }

            bits = 134217728
            if (winParams.flags and bits != 0) {
                this.mNavBarAvailable = true
            }
        }

        this.config = SystemBarManager.SystemBarConfig(activity, this.mStatusBarAvailable, this.mNavBarAvailable)
        if (!this.config.hasNavigtionBar()) {
            this.mNavBarAvailable = false
        }

        if (this.mStatusBarAvailable) {
            this.setupStatusBarView(activity, decorViewGroup)
        }

        if (this.mNavBarAvailable) {
            this.setupNavBarView(activity, decorViewGroup)
        }

    }

    fun setNavigationBarTintEnabled(enabled: Boolean) {
        this.isNavBarTintEnabled = enabled
        if (this.mNavBarAvailable) {
            this.mNavBarTintView!!.visibility = if (enabled) View.VISIBLE else View.GONE//baibaibai    0:8
        }

    }


    fun setTintColor(color: Int) {
        this.setStatusBarTintColor(color)
        this.setNavigationBarTintColor(color)
    }

    /**
     * 设置状态栏为某张图片
     */
    fun setTintResource(res: Int) {
        this.setStatusBarTintResource(res)
        this.setNavigationBarTintResource(res)
    }

    fun setTintDrawable(drawable: Drawable) {
        this.setStatusBarTintDrawable(drawable)
        this.setNavigationBarTintDrawable(drawable)
    }

    fun setTintAlpha(alpha: Float) {
        this.setStatusBarAlpha(alpha)
        this.setNavigationBarAlpha(alpha)
    }

    private fun setStatusBarTintColor(color: Int) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView!!.setBackgroundColor(color)
        }

    }

    private fun setStatusBarTintResource(res: Int) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView!!.setBackgroundResource(res)
        }

    }

    private fun setStatusBarTintDrawable(drawable: Drawable) {
        if (this.mStatusBarAvailable) {
            this.mStatusBarTintView!!.setBackgroundDrawable(drawable)
        }

    }

    @TargetApi(11)
    private fun setStatusBarAlpha(alpha: Float) {
        if (this.mStatusBarAvailable && VERSION.SDK_INT >= 11) {
            this.mStatusBarTintView!!.alpha = alpha
        }

    }

    private fun setNavigationBarTintColor(color: Int) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView!!.setBackgroundColor(color)
        }

    }

    private fun setNavigationBarTintResource(res: Int) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView!!.setBackgroundResource(res)
        }

    }

    private fun setNavigationBarTintDrawable(drawable: Drawable) {
        if (this.mNavBarAvailable) {
            this.mNavBarTintView!!.setBackgroundDrawable(drawable)
        }

    }

    @TargetApi(11)
    private fun setNavigationBarAlpha(alpha: Float) {
        if (this.mNavBarAvailable && VERSION.SDK_INT >= 11) {
            this.mNavBarTintView!!.alpha = alpha
        }

    }

    private fun setupStatusBarView(context: Context, decorViewGroup: ViewGroup) {
        this.mStatusBarTintView = View(context)
        val params = android.widget.FrameLayout.LayoutParams(-1, this.config.statusBarHeight)
        params.gravity = 48
        if (this.mNavBarAvailable && !this.config.isNavigationAtBottom) {
            params.rightMargin = this.config.navigationBarWidth
        }

        this.mStatusBarTintView!!.layoutParams = params
        this.mStatusBarTintView!!.setBackgroundColor(DEFAULT_TINT_COLOR)
        this.mStatusBarTintView!!.visibility = View.VISIBLE//baibaibai     8
        decorViewGroup.addView(this.mStatusBarTintView)
    }

    private fun setupNavBarView(context: Context, decorViewGroup: ViewGroup) {
        this.mNavBarTintView = View(context)
        val params: android.widget.FrameLayout.LayoutParams
        if (this.config.isNavigationAtBottom) {
            params = android.widget.FrameLayout.LayoutParams(-1, this.config.navigationBarHeight)
            params.gravity = 80
        } else {
            params = android.widget.FrameLayout.LayoutParams(this.config.navigationBarWidth, -1)
            params.gravity = 5
        }

        this.mNavBarTintView!!.layoutParams = params
        this.mNavBarTintView!!.setBackgroundColor(DEFAULT_TINT_COLOR)
        this.mNavBarTintView!!.visibility = View.VISIBLE//baibaibai     8
        decorViewGroup.addView(this.mNavBarTintView)
    }

    class SystemBarConfig (activity: Activity, private val mTranslucentStatusBar: Boolean, private val mTranslucentNavBar: Boolean) {
        val statusBarHeight: Int
        val actionBarHeight: Int
        private val mHasNavigationBar: Boolean
        val navigationBarHeight: Int
        val navigationBarWidth: Int
        private val mInPortrait: Boolean
        private val mSmallestWidthDp: Float

        val isNavigationAtBottom: Boolean
            get() = this.mSmallestWidthDp >= 600.0f || this.mInPortrait

        val pixelInsetBottom: Int
            get() = if (this.mTranslucentNavBar && this.isNavigationAtBottom) this.navigationBarHeight else 0

        val pixelInsetRight: Int
            get() = if (this.mTranslucentNavBar && !this.isNavigationAtBottom) this.navigationBarWidth else 0

        init {
            val res = activity.resources
            this.mInPortrait = res.configuration.orientation == 1
            this.mSmallestWidthDp = this.getSmallestWidthDp(activity)
            this.statusBarHeight = this.getInternalDimensionSize(res, "status_bar_height")
            this.actionBarHeight = this.getActionBarHeight(activity)
            this.navigationBarHeight = this.getNavigationBarHeight(activity)
            this.navigationBarWidth = this.getNavigationBarWidth(activity)
            this.mHasNavigationBar = this.navigationBarHeight > 0
        }

        @TargetApi(14)
        private fun getActionBarHeight(context: Context): Int {
            var result = 0
            if (VERSION.SDK_INT >= 14) {
                val tv = TypedValue()
                context.theme.resolveAttribute(16843499, tv, true)
                result = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
            }

            return result
        }

        @TargetApi(14)
        private fun getNavigationBarHeight(context: Context): Int {
            val res = context.resources
            val result = 0
            if (VERSION.SDK_INT >= 14 && this.hasNavBar(context)) {
                val key: String
                if (this.mInPortrait) {
                    key = "navigation_bar_height"
                } else {
                    key = "navigation_bar_height_landscape"
                }

                return this.getInternalDimensionSize(res, key)
            } else {
                return result
            }
        }

        @TargetApi(14)
        private fun getNavigationBarWidth(context: Context): Int {
            val res = context.resources
            val result = 0
            return if (VERSION.SDK_INT >= 14 && this.hasNavBar(context)) this.getInternalDimensionSize(res, "navigation_bar_width") else result
        }

        @TargetApi(14)
        private fun hasNavBar(context: Context): Boolean {
            val res = context.resources
            val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
            if (resourceId != 0) {
                var hasNav = res.getBoolean(resourceId)
                if ("1" == SystemBarManager.sNavBarOverride) {
                    hasNav = false
                } else if ("0" == SystemBarManager.sNavBarOverride) {
                    hasNav = true
                }

                return hasNav
            } else {
                return !ViewConfiguration.get(context).hasPermanentMenuKey()
            }
        }

        private fun getInternalDimensionSize(res: Resources, key: String): Int {
            var result = 0
            val resourceId = res.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }

            return result
        }

        @SuppressLint("NewApi")
        private fun getSmallestWidthDp(activity: Activity): Float {
            val metrics = DisplayMetrics()
            if (VERSION.SDK_INT >= 16) {
                activity.windowManager.defaultDisplay.getRealMetrics(metrics)
            } else {
                activity.windowManager.defaultDisplay.getMetrics(metrics)
            }

            val widthDp = metrics.widthPixels.toFloat() / metrics.density
            val heightDp = metrics.heightPixels.toFloat() / metrics.density
            return Math.min(widthDp, heightDp)
        }

        fun hasNavigtionBar(): Boolean {
            return this.mHasNavigationBar
        }

        fun getPixelInsetTop(withActionBar: Boolean): Int {
            return (if (this.mTranslucentStatusBar) this.statusBarHeight else 0) + if (withActionBar) this.actionBarHeight else 0
        }

        companion object {
            private val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"
            private val NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height"
            private val NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape"
            private val NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width"
            private val SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar"
        }
    }

    companion object {
        val DEFAULT_TINT_COLOR = -1728053248
        private var sNavBarOverride: String? = null

        init {
            if (VERSION.SDK_INT >= 19) {
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val m = c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null as Any?, "qemu.hw.mainkeys") as String
                } catch (var2: Throwable) {
                    sNavBarOverride = null
                }

            }

        }
    }
}

