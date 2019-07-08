package com.jy.zqizhong;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApIServer {
    String baseurl = "https://www.wanandroid.com/";

    @GET("project/list/1/json?cid=294")
    Observable<WanAndroidBean> getData();

}
