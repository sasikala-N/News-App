package com.example.news.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.news.Model.TechArticle;
import com.example.news.Repository.TechRepository;

import java.util.List;

public class TechViewModel extends AndroidViewModel {
    private TechRepository repo;
    private LiveData<List<TechArticle>>list;
    public TechViewModel(@NonNull Application application) {
        super(application);
        repo=new TechRepository(application);
        list=repo.getTechNews();
    }
    public LiveData<List<TechArticle>>getTechNews(){
        return list;
    }
    public void insert(List<TechArticle>list){
        repo.insert(list);
    }
    public void delete(){
        repo.delete();
    }
}
