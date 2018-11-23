package bai.bai.bai.demo.retrofit

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

/**
 * 用于描述网络请求的接口
 * 如baseUrl为:http://pbs-snp.wangtest.cn/api/posapp/
 */
interface IMyApi {

    /**
     * @POST注解的作用:采用Post方法发送网络请求
     * 其中返回类型为Call<MyPostTestBean>，MyPostTestBean是接收数据的类
     * 如果想直接获得Responsebody中的内容(原始的json字符串)，可以定义网络请求返回值为Call<ResponseBody>
     *
     * @Headers和@Header的作用一样，@Headers用于固定的，@Header可用于不固定的
     * @FromUrlEncoded与@Field（或@FieldMap）要联合起来使用，添加数据体，数据体放在请求体上
     * @Multpart与@Part(或@PartMap，使用于post请求)一起连用，表示请求体支持文件上传
     */
    @POST("payment/list")
    fun testPostQuest(@Query("device_en") device_en: String): Call<MyPostTestBean>

    @Headers("Accept-Language:zh-CN")
    @POST("payment/list")
    @FormUrlEncoded
    fun testPostQuestRxjava(@Field("device_en") device_en: String): Single<MyPostTestBean>

    //===============================================================================================================//

    /**
     * @GET注解的作用:采用Get方法发送网络请求
     *
     * @Query或@QueryMap也是添加数据体（URL后？后面的,不可随便写），与Field和FieldMap的不同是这个是提现在URL上
     */
    //会追加到baseUrl后面，即:http://gc.ditu.aliyun.com/geocoding
    @GET("geocoding")
    //追加到@GET后的网址后面，即:http://gc.ditu.aliyun.com/geocoding?a=邯郸市
    fun testGetQuest(@Query("a") a: String): Call<MyGetTestBean>

    //===============================================================================================================//

    /**
     * @HTTP注解的作用:替换@GET、@POST、@PUT、@DELETE、@HEAD注解及更多拓展作用
     * 具体使用:通过属性method、path、hasBody进行设置
     * method:请求方式
     * path:直接替换，可以是任意字母
     * hasBody:GET请求必须没有请求体，所以必须是false
     *
     * @Paht直接替换掉@HTTP里的path
     */
    @HTTP(method = "GET", path = "{someThing}", hasBody = false)
    fun testHttp(@Path("someThing") someThing: String): Call<MyGetTestBean>

}