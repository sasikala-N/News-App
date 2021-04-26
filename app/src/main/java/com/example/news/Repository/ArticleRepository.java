package com.example.news.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.news.Dao.ArticleDao;
import com.example.news.DataBase.NewsDatabase;
import com.example.news.Model.Article;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.List;

public class ArticleRepository {
    private NewsDatabase db;
    private LiveData<List<Article>>list;
    public ArticleRepository(Application application) {
        db=NewsDatabase.getInstance(application);
        list=db.getArticleDao().getAllNews();
    }
    public LiveData<List<Article>>getNewsList(){
        return list;
    }
    public void insert(List<Article>list){
     new InsertList(db).execute(list);
    }
    public void delete(){
        new Delete(db).execute();
    }
    class InsertList extends AsyncTask<List<Article>,Void,Void>{
     private ArticleDao dao;
     InsertList(NewsDatabase newsDatabase){
       dao=newsDatabase.getArticleDao();
     }

        @Override
        protected Void doInBackground(List<Article>... lists) {
         dao.insert(lists[0]);
            return null;
        }
    }
    class Delete extends AsyncTask<Void,Void,Void>{
     private ArticleDao dao;
     Delete(NewsDatabase newsDatabase){
         dao=newsDatabase.getArticleDao();
     }
        @Override
        protected Void doInBackground(Void... voids) {
         dao.delete();
            return null;
        }
    }
}
