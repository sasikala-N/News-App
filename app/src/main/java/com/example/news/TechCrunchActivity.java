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

import com.example.news.Adapter.OnRecyclerViewItemClickListener;
import com.example.news.Adapter.TechAdapter;
import com.example.news.Model.Article;
import com.example.news.Model.TechArticle;
import com.example.news.Model.TechResponceModel;
import com.example.news.NetWork.ApiClient;
import com.example.news.NetWork.ApiInterface;
import com.example.news.Trail.Connectivity;
import com.example.news.ViewModel.TechViewModel;
import com.example.news.Web.WebActivity;
import com.example.news.databinding.ActivityTechCrunchBinding;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechCrunchActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private static final String TAG = TechCrunchActivity.class.getName();
    ActivityTechCrunchBinding binding;
    private static final String API_KEY = "a62872ce095e4200ac68b925ce71f8a4";
    private TechViewModel model;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTechCrunchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        checkInternetConenction();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top headlines from TechCrunch");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.techrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.techrecyclerview.setHasFixedSize(true);
        final ApiInterface apiService=ApiClient.getClient().create(ApiInterface.class);
        Call<TechResponceModel>call=apiService.getLatestNews("techcrunch",API_KEY);
        progressDialog = new ProgressDialog(TechCrunchActivity.this);
        progressDialog.setMessage("  Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        call.enqueue(new Callback<TechResponceModel>() {
            @Override
            public void onResponse(Call<TechResponceModel> call, Response<TechResponceModel> response) {
                progressDialog.dismiss();
                if(response.body().getStatus().equals("ok")){
                    List<TechArticle>articleList=response.body().getArticles();
                    if(articleList.size()>0){
                     // repo.insert(articleList);
                        model.insert(articleList);
                    }
                }
                Log.d(TAG, "onResponse: "+response);
//                Toast.makeText(TechCrunchActivity.this,"Success",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<TechResponceModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        model=new ViewModelProvider(this).get(TechViewModel.class);
        model.getTechNews().observe(this, new Observer<List<TechArticle>>() {
            @Override
            public void onChanged(List<TechArticle> techArticles) {
                TechAdapter techAdapter=new TechAdapter(techArticles,getApplicationContext());
                techAdapter.getupdate(techArticles);
                techAdapter.setOnRecyclerViewItemClickListener(TechCrunchActivity.this);
                binding.techrecyclerview.setAdapter(techAdapter);
            }
        });

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
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            startActivity(new Intent(TechCrunchActivity.this, Connectivity.class));
            return false;
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.delete();
    }
    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.technews: TechArticle article = (TechArticle) view.getTag();
                if(!TextUtils.isEmpty(article.getUrl())) {
                    Log.e(TAG, "WEBURL"+article.getUrl());
                    Intent webActivity = new Intent(this, WebActivity.class);
                    webActivity.putExtra("URL",article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }
}