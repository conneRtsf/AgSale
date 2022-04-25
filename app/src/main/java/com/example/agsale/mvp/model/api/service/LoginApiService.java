package com.example.agsale.mvp.model.api.service;

import android.icu.text.IDNA;

import com.example.agsale.mvp.model.bean.ReqBackData;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApiService {

//        @FormUrlEncoded
//        @POST("api/comments.163")
//        Call<Object> postDataCall(@Field("format") String format);

        @FormUrlEncoded
        @POST("basic/user/login")
        Call<ResponseBody> getPostData1(@FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("basic/user/login")
        Call<ReqBackData<IDNA.Info>> getPostData2(@FieldMap Map<String, String> map);

}
