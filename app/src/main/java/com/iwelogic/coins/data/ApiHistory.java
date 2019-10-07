package com.iwelogic.coins.data;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiHistory {

    @GET("data/histoday")
    Observable<Object> getHistory(@QueryMap HashMap<String, Object> queries);
}
