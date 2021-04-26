package com.example.news.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.news.Model.AppleArticle;
import com.example.news.Model.Article;
import com.example.news.Repository.AppleRepository;
import com.example.news.Repository.ArticleRepository;

import java.util.List;

public class AppleViewModel extends AndroidViewModel {
    private AppleRepository repo;
    private LiveData<List<AppleArticle>> list;
    public AppleViewModel(@NonNull Application application) {
        super(application);
        repo=new AppleRepository(application);
        list=repo.getappleList();
    }
    public LiveData<List<AppleArticle>>getappleNews(){
        return list;
    }
    public void insertapplenews(List<AppleArticle>list){
        repo.insertapplenews(list);
    }
    public void deleteapplenews(){
        repo.deleteappplenews();
    }
}
