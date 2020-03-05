package com.example.spacebottomview.interfaces;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api_Client {
   String BASE_URL = "https://price-api.datayuge.com/";

    @GET("api/v1/compare/search?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm&product=Iphone&filter=brand%3Aapple&price_start=20000&price_end=30000&page=1https://price-api.datayuge.com/api/v1/compare/price?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm")
    Call<ResponseBody> getJSON();

    @GET("api/v1/compare/search?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm&mobile")
    Call<ResponseBody> getMobile();

    @GET Call<ResponseBody> getUsers(@Url String url);


}
