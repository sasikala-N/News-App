package com.example.news.NetWork;

import com.example.news.Model.AppleResponseModel;
import com.example.news.Model.TechCrunchResponceModel;
import com.example.news.Model.TechResponceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<TechResponceModel> getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);
    @GET("top-headlines")
    Call<TechCrunchResponceModel> getLatestUsNews(@Query("country") String country,@Query("category") String category, @Query("apiKey") String apiKey);
    @GET("everything")
    Call<AppleResponseModel> getLatestAppleNews(@Query("q") String q, @Query("from") String from, @Query("to") String to,@Query("sortBy")String sortBy,@Query("apiKey") String apiKey);

}
