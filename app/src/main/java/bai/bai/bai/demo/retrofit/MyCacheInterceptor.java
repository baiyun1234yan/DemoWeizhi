package bai.bai.bai.demo.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器
 */
public class MyCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截Request对象
        Request request = chain.request();
        //判断有无网络连接
//        //有网络，缓存时间短,缓存90s
//        String cacheControl = request.cacheControl().toString();
//        //这里返回的就是我们获取到的响应头，添加缓存配置返回
//        return response.newBuilder()
//                .removeHeader("Pragma")
//                .header("Cache-Control", "public, max-age=90")
//                .build();
        return chain.proceed(request).newBuilder().build();

    }
}
