package com.example.news.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.news.Dao.AppleDao;
import com.example.news.Dao.ArticleDao;
import com.example.news.DataBase.NewsDatabase;
import com.example.news.Model.AppleArticle;
import com.example.news.Model.Article;

import java.security.PublicKey;
import java.util.List;

public class AppleRepository {
    private NewsDatabase db;
    private LiveData<List<AppleArticle>> list;
    public AppleRepository(Application application){
        db=NewsDatabase.getInstance(application);
        list=db.getApplDao().getApplenews();
    }

    public LiveData<List<AppleArticle>>getappleList(){
        return list;
    }
    public void insertapplenews(List<AppleArticle>list){
        new InsertAppleList(db).execute(list);
    }
    public void deleteappplenews(){
        new DeleteApplenews(db).execute();
    }
   class InsertAppleList extends AsyncTask<List<AppleArticle>,Void,Void>{
    private AppleDao adao;
    InsertAppleList(NewsDatabase newsDatabase){
        adao=newsDatabase.getApplDao();
    }
       @Override
       protected Void doInBackground(List<AppleArticle>... lists) {
        adao.insert(lists[0]);
           return null;
       }
   }
   class DeleteApplenews extends AsyncTask<Void,Void,Void>{
      private AppleDao dao;
      DeleteApplenews(NewsDatabase newsDatabase){
          dao=newsDatabase.getApplDao();
      }
       @Override
       protected Void doInBackground(Void... voids) {
          dao.delete();
           return null;
       }
   }

}
