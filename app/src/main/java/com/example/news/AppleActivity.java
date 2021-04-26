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

import com.example.news.Adapter.AppleAdapter;
import com.example.news.Adapter.OnRecyclerViewItemClickListener;
import com.example.news.Fragment.BlankFragment;
import com.example.news.Model.AppleArticle;
import com.example.news.Model.AppleResponseModel;
import com.example.news.Model.Article;
import com.example.news.NetWork.ApiClient;
import com.example.news.NetWork.ApiInterface;
import com.example.news.Trail.Connectivity;
import com.example.news.ViewModel.AppleViewModel;
import com.example.news.Web.WebActivity;
import com.example.news.databinding.ActivityAppleAtcivityBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppleActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private static final String TAG = AppleActivity.class.getName();
    private ActivityAppleAtcivityBinding binding;
    private static final String API_KEY = "a62872ce095e4200ac68b925ce71f8a4";
    private static String date;
    private ProgressDialog progressDialog;
    private AppleViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityAppleAtcivityBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        checkInternetConenction();
        date=getYesterdayDateString();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Apple Articles");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.applerecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.applerecyclerview.setHasFixedSize(true);
        final ApiInterface apiService= ApiClient.getClient().create(ApiInterface.class);
        Call<AppleResponseModel> call=apiService.getLatestAppleNews("apple",date,date,"popularity",API_KEY);
        progressDialog = new ProgressDialog(AppleActivity.this);
        progressDialog.setMessage("  Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        call.enqueue(new Callback<AppleResponseModel>() {
            @Override
            public void onResponse(Call<AppleResponseModel> call, Response<AppleResponseModel> response) {
                progressDialog.dismiss();
                if(response.body().getStatus().equals("ok")){
                    List<AppleArticle>appleArticleList=response.body().getArticles();
                    if(appleArticleList.size()>0){
                        model.insertapplenews(appleArticleList);
                    }
                    Log.d(TAG, "onResponse: "+response);
                }
            }

            @Override
            public void onFailure(Call<AppleResponseModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
        model=new ViewModelProvider(this).get(AppleViewModel.class);
        model.getappleNews().observe(this, new Observer<List<AppleArticle>>() {
            @Override
            public void onChanged(List<AppleArticle> appleArticles) {
                AppleAdapter adapter=new AppleAdapter(appleArticles,getApplicationContext());
                adapter.getupdate(appleArticles);
                adapter.setOnRecyclerViewItemClickListener(AppleActivity.this);
                binding.applerecyclerview.setAdapter(adapter);
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
            startActivity(new Intent(AppleActivity.this, Connectivity.class));
            return false;
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.deleteapplenews();
    }
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()){
            case R.id.applenews:AppleArticle apple=(AppleArticle)view.getTag();
            if(!TextUtils.isEmpty(apple.getUrl())){
                Log.e(TAG,"WEBURL"+apple.getUrl());
                Intent webActivity=new Intent(this, WebActivity.class);
                webActivity.putExtra("URL",apple.getUrl());
                startActivity(webActivity);
            }
        }
    }
}