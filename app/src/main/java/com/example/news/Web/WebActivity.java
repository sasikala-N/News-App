package com.example.news.Web;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.news.AppleActivity;
import com.example.news.BusinessNewsActivity;
import com.example.news.R;
import com.example.news.Trail.Connectivity;
import com.example.news.databinding.ActivityWebActivtityBinding;

public class WebActivity extends AppCompatActivity {
    private ActivityWebActivtityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityWebActivtityBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        checkInternetConenction();
        final String url=getIntent().getStringExtra("URL");
        binding.webview.loadUrl(url);
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
            startActivity(new Intent(WebActivity.this, Connectivity.class));
            return false;
        }
        return false;
    }
}