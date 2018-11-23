package bai.bai.bai.demo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import bai.bai.bai.demo.R
import bai.bai.bai.demo.systembar.SystemBarTintManager
//import com.readystatesoftware.systembartint.SystemBarTintManager
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : Activity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        // 创建状态栏的管理实例
        val tintManager = SystemBarTintManager(this)
        // 激活状态栏设置
        tintManager.isStatusBarTintEnabled = true
        // 设置一个颜色给系统栏
        tintManager.setStatusBarTintColor(resources.getColor(R.color.base_action_bar_bg))

        initListener()

    }

    private fun initListener() {
        btn_choose_snap_pay.setOnClickListener(this)
        btn_choose_location.setOnClickListener(this)
        btn_choose_ip.setOnClickListener(this)
        btn_choose_java.setOnClickListener(this)
        btn_choose_view.setOnClickListener(this)
        btn_choose_assets_file.setOnClickListener(this)
        btn_choose_client.setOnClickListener(this)
        btn_choose_date.setOnClickListener(this)
        btn_choose_rxjava.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_choose_snap_pay -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.btn_choose_location -> {
                startActivity(Intent(this, LocationActivity::class.java))
            }

            R.id.btn_choose_ip -> {
                startActivity(Intent(this, IpActivity::class.java))
            }

            R.id.btn_choose_java -> {
                startActivity(Intent(this, JavaViewTestActivity::class.java))
            }

            R.id.btn_choose_view -> {
                startActivity(Intent(this, ViewTestActivity::class.java))
            }

            R.id.btn_choose_assets_file -> {
                startActivity(Intent(this, AssetsFileActivity::class.java))
            }

            R.id.btn_choose_client -> {
                startActivity(Intent(this, RetrofitActivity::class.java))
            }

            R.id.btn_choose_date -> {
                startActivity(Intent(this, DateActivity::class.java))
            }

            R.id.btn_choose_rxjava -> {
                startActivity(Intent(this, RxjavaActivity::class.java))
            }

        }
    }

}
