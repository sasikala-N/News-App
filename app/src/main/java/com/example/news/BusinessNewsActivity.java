package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.news.Adapter.BusinessNewsAdapter;
import com.example.news.Adapter.OnRecyclerViewItemClickListener;
import com.example.news.Model.Article;
import com.example.news.Model.TechCrunchResponceModel;
import com.example.news.NetWork.ApiClient;
import com.example.news.NetWork.ApiInterface;
import com.example.news.Trail.Connectivity;
import com.example.news.ViewModel.ArticleViewModel;
import com.example.news.Web.WebActivity;
import com.example.news.databinding.ActivityBusinessNewsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessNewsActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private static final String TAG = BusinessNewsActivity.class.getName();
  private static final String API_KEY="Your Api_key";
  private static ActivityBusinessNewsBinding binding;
  private ArticleViewModel model;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityBusinessNewsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        checkInternetConenction();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top business headlines in the US");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);
        final ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);
        Call<TechCrunchResponceModel> call=apiService.getLatestUsNews("us","business",API_KEY);
        progressDialog = new ProgressDialog(BusinessNewsActivity.this);
        progressDialog.setMessage("  Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        call.enqueue(new Callback<TechCrunchResponceModel>() {
            @Override
            public void onResponse(Call<TechCrunchResponceModel>call, Response<TechCrunchResponceModel> response) {
                progressDialog.dismiss();
                if(response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if(articleList.size()>0) {
                        model.insert(articleList);
                    }
                }
                Log.d(TAG, "onResponse: "+response);
            }
            @Override
            public void onFailure(Call<TechCrunchResponceModel>call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        model=new ViewModelProvider(this).get(ArticleViewModel.class);
        model.getallNews().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                final BusinessNewsAdapter mainArticleAdapter = new BusinessNewsAdapter(articles,getApplicationContext());
                mainArticleAdapter.getupdate(articles);
                mainArticleAdapter.setOnRecyclerViewItemClickListener(BusinessNewsActivity.this);
                binding.recyclerview.setAdapter(mainArticleAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       model.delete();
    }
    private boolean checkInternetConenction() {
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
//            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            startActivity(new Intent(BusinessNewsActivity.this, Connectivity.class));
            return false;
        }
        return false;
    }
    @Override
    public void onItemClick(int position, View view) {
        switch(view.getId()){
            case R.id.Businessnews:Article article=(Article) view.getTag();
            if(!TextUtils.isEmpty(article.getUrl())){
                Log.e(TAG, "WEBURL"+article.getUrl());
                Intent webActivity=new Intent(this, WebActivity.class);
                webActivity.putExtra("URL",article.getUrl());
                startActivity(webActivity);
            }
            break;
        }
    }
}
