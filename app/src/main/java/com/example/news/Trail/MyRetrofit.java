package com.example.news.Trail;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.news.Dao.TechDao;
import com.example.news.DataBase.NewsDatabase;
import com.example.news.Model.TechArticle;
import com.example.news.Model.TechResponceModel;
import com.example.news.NetWork.ApiClient;
import com.example.news.NetWork.ApiInterface;
import com.example.news.Repository.TechRepository;
import com.example.news.TechCrunchActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class MyRetrofit {
////    private static final String TAG = MyRetrofit.class.getName();
////   TechDao dao;
////    ProgressDialog progressDoalog;
////    final String API_KEY = "a62872ce095e4200ac68b925ce71f8a4";
////    public void GetRetrofit(Context context) {
////        dao= NewsDatabase.getInstance(context).getTechDao();
////        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
////        Call<TechResponceModel> call = apiService.getLatestNews("techcrunch", API_KEY);
//////        progressDoalog = new ProgressDialog(context);
//////        progressDoalog.setMessage("  Loading...");
//////        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//////        progressDoalog.setProgress(0);
//////        progressDoalog.show();
////        call.enqueue(new Callback<TechResponceModel>() {
////            @Override
////            public void onResponse(Call<TechResponceModel> call, Response<TechResponceModel> response) {
////                //progressDoalog.dismiss();
////                if (response.body().getStatus().equals("ok")) {
////                    List<TechArticle> articleList = response.body().getArticles();
////                    if (articleList.size() > 0) {
////                        dao.insert(articleList);
////                    }
////                }
////                Log.d(TAG, "onResponse: " + response);
//////                Toast.makeText(TechCrunchActivity.this,"Success",Toast.LENGTH_LONG).show();
////            }
////
////            @Override
////            public void onFailure(Call<TechResponceModel> call, Throwable t) {
////                //progressDoalog.dismiss();
//////                Toast.makeText(TechCrunchActivity.this, "Fail", Toast.LENGTH_LONG).show();
////            }
////        });
////    }
//}
