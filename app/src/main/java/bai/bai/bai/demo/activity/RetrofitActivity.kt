package bai.bai.bai.demo.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import bai.bai.bai.demo.retrofit.IMyApi
import bai.bai.bai.demo.retrofit.MyCacheInterceptor
import bai.bai.bai.demo.retrofit.MyGetTestBean
import bai.bai.bai.demo.retrofit.MyPostTestBean
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory

/**
 * Retrofit
 */
class RetrofitActivity : Activity(), View.OnClickListener {

    private lateinit var mIMyApi: IMyApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        btn_retrofit_post.setOnClickListener(this)
        btn_retrofit_get.setOnClickListener(this)
        btn_retrofit_http.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btn_retrofit_post -> {
//                testPostRequestMethed()
                testPostRequestRxjavaMethed()
            }

            R.id.btn_retrofit_get -> {
                testGetRequestMethed()
            }

            R.id.btn_retrofit_http -> {
                testHttpMethed()
            }

        }
    }


    //region post请求
    /**
     * post请求(Call)
     */
    private fun testPostRequestMethed() {

        val okHttpClient = OkHttpClient.Builder()
                //添加拦截器（可添加多个），把将要发送的request拦截下来，然后进行一些操作，比如添加消息头
                .addInterceptor { chain ->
                    val original = chain.request()//拦截request对象
                    val request = original.newBuilder()
                            .header("Accept-Language", "zh-CN")
                            .header("Accept-Timezone", "Asia/Shanghai")
                            .header("Content-Type", "text/plain")
                            .method(original.method(), original.body())
                            .build()
                    chain.proceed(request)//响应头
                }
//                .addInterceptor(MyCacheInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                //网络执行器
                .client(okHttpClient)
                //设置网络请求的Url地址
                .baseUrl("http://pbs-test.snappay.ca/api/posapp/")
                //添加数据解析器（也可以是GonsConverterFactory，根据自己喜好，注意要添加依赖）
                .addConverterFactory(FastJsonConverterFactory.create())
                //可以不添加，但是为了Retrofit一般都是和RxJava连用的，Retrofit默认的是Call<泛型>,也可以是Observable<泛型>
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        //创建网络请求接口实例并配置网络请求参数
        mIMyApi = retrofit.create(IMyApi::class.java)
        //对发送的数据进行封装
        val data = mIMyApi.testPostQuest("jiyfqbig")
        //发送请求(异步)
        data.enqueue(object : Callback<MyPostTestBean> {
            override fun onFailure(call: Call<MyPostTestBean>?, t: Throwable?) {
                Log.d("baibai", "post请求 -> Retrofit -> enqueue(Call) -> onFailure() -> ${t!!.message}")
            }

            override fun onResponse(call: Call<MyPostTestBean>?, response: Response<MyPostTestBean>?) {
                Log.d("baibai", "post请求 -> Retrofit -> enqueue(Call) -> onResponse -> ${response!!.body()!!.msg}")
            }

        })
        Log.d("baibai", "我是post发出的请求数据")
    }

    /**
     * post请求(Single)
     */
    private fun testPostRequestRxjavaMethed() {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()//拦截request对象
                    val request = original.newBuilder()
                            .header("Accept-Language", "zh-CN")
                            .header("Accept-Timezone", "Asia/Shanghai")
                            .header("Content-Type", "text/plain")
                            .method(original.method(), original.body())
                            .build()
                    chain.proceed(request)//响应头
                }
//                .addInterceptor(MyCacheInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://pbs-test.snappay.ca/api/posapp/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        mIMyApi = retrofit.create(IMyApi::class.java)
        //对发送的数据进行封装
        mIMyApi.testPostQuestRxjava("jiyfqbig")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<MyPostTestBean> {
                    override fun onSuccess(t: MyPostTestBean) {
                        Log.d("baibai", "post请求 -> Retrofit -> Rxjava(Single) -> onSuccess -> ${t.msg}")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("baibai", "post请求 -> Retrofit -> Rxjava(Single) -> onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        Log.d("baibai", "post请求 -> Retrofit -> Rxjava(Single) -> onError -> ${e.message}")
                    }

                })
    }
    //endregion


    //region get请求
    /**
     * get请求
     */
    private fun testGetRequestMethed() {

        val retrofit = Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        mIMyApi = retrofit.create(IMyApi::class.java)
        val data = mIMyApi.testGetQuest("邯郸市")
        data.enqueue(object : Callback<MyGetTestBean> {
            override fun onFailure(call: Call<MyGetTestBean>?, t: Throwable?) {
                Log.d("baibai", "get请求 -> Retrofit -> enqueue(Call) -> onFailure() -> ${t!!.message}")
            }

            override fun onResponse(call: Call<MyGetTestBean>?, response: Response<MyGetTestBean>?) {
                Log.d("baibai", "get请求 -> Retrofit -> enqueue(Call) -> onResponse() -> 经度是 ：${response!!.body()!!.lon}")
            }

        })
        Log.d("baibai", "我是get发出的请求数据")
    }
    //endregion


    //region http注解
    /**
     * http注解
     */
    private fun testHttpMethed() {

        val retrofit = Retrofit.Builder()
                .baseUrl("http://gc.ditu.aliyun.com/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        mIMyApi = retrofit.create(IMyApi::class.java)
        val data = mIMyApi.testHttp("geocoding")
        data.enqueue(object : Callback<MyGetTestBean> {
            override fun onFailure(call: Call<MyGetTestBean>?, t: Throwable?) {
                Log.d("baibai", "http注解 -> Retrofit -> enqueue(Call) -> onFailure() -> ${t!!.message}")
            }

            override fun onResponse(call: Call<MyGetTestBean>?, response: Response<MyGetTestBean>?) {
                Log.d("baibai", "http注解 -> Retrofit -> enqueue(Call) -> onResponse() -> 经度是 ：${response!!.body()!!.lon}")
            }

        })
        Log.d("baibai", "我是http注解")
    }
    //endregion

}
