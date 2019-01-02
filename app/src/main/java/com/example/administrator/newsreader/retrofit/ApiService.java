package com.example.administrator.newsreader.retrofit;


import com.example.administrator.newsreader.bean.NewsGson;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/23.
 */

public interface ApiService{

    @GET("{type}/")
    Observable <NewsGson> getNewsData(@Path("type") String type,@Query("key")String key, @Query("num") String num, @Query("page") int page);


}
