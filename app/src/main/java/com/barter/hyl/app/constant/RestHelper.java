package com.barter.hyl.app.constant;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.barter.hyl.app.activity.LoginActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by GuoGai on 2016/7/18.
 */
public class RestHelper {
    /**
     * 获取BaseRetrofit
     *
     * @return
     */
    public static Retrofit getBaseRetrofit(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getRestClient(context))
                .baseUrl(AppInterfaceAddress.BASE_URL)
                .build();
        return retrofit;
    }

    /**
     * 地图请求
     *
     * @param context
     * @return
     */
    public static Retrofit getBaiDuRetrofit(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getRestClient(context))
                .baseUrl("http://api.map.baidu.com/")
                .build();
        return retrofit;
    }

    /**
     * 返回Json数据String的时候
     * String 返回的参数
     *
     * @param context
     * @return
     */
    public static Retrofit getStringBaseRetrofit(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getRestClient(context))
                .baseUrl(AppInterfaceAddress.BASE_URL)
                .build();
        return retrofit;
    }

    /**
     * webView 请求
     */
    public static Retrofit getBaseRetrofit(Context context, String Url) {
        Retrofit retrofit = new Retrofit.Builder()
                // .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getRestClient(context))
                .baseUrl(Url)
                .build();
        return retrofit;
    }


    /**
     * 返回OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getRestClient(final Context context) {
        //公共参数拦截器(区分在哪里接收)
        Interceptor commonParamsInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request();
                HttpUrl commonUrl = originalRequest.url()
                        .newBuilder()
                        .addQueryParameter(AppConstant.APP_TYPE, "1")
                        .addQueryParameter(AppConstant.VERSION, AppHelper.getVersion(context))
                        .addQueryParameter(AppConstant.TOKEN, UserInfoHelper.getUserId(context))
                        .addQueryParameter(AppConstant.PHONEMODEL, AppHelper.getSystemModel())
                        .addQueryParameter(AppConstant.SYSETEMMODEL, AppHelper.getSystemModel())
                        .build();
                Request commonRequest = originalRequest.newBuilder().url(commonUrl).build();
                Log.d("----->", commonRequest + AppConstant.TOKEN);
                return chain.proceed(commonRequest);
            }
        };
        //Log拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("RestLogging", message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //cache拦截器
        File cacheFile = new File(context.getExternalCacheDir(), "QiaoGeCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkHelper.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);

                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    try {

                        String result = buffer.clone().readString(StandardCharsets.UTF_8);

                        JSONObject jsonObject = new JSONObject(result);

                        int code = jsonObject.getInt("code");
                        if(code==-10001) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                if (NetWorkHelper.isNetworkAvailable(context)) {
                    int maxAge = 0 * 60;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //client
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(commonParamsInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        return client;
    }
}
