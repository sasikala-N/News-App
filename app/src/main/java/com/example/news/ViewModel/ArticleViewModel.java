package com.example.news.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.news.Model.Article;
import com.example.news.Repository.ArticleRepository;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
   private ArticleRepository repo;
   private LiveData<List<Article>>list;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repo=new ArticleRepository(application);
        list=repo.getNewsList();
    }
    public LiveData<List<Article>>getallNews(){
        return list;
    }
    public void insert(List<Article>list){
        repo.insert(list);
    }
    public void delete(){
        repo.delete();
    }
}
