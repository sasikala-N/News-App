package com.example.news.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.news.Dao.ArticleDao;
import com.example.news.Dao.TechDao;
import com.example.news.DataBase.NewsDatabase;
import com.example.news.Model.Article;
import com.example.news.Model.TechArticle;

import java.util.List;

public class TechRepository {
    private NewsDatabase db;
    private LiveData<List<TechArticle>>list;
    public TechRepository(Application application){
        db=NewsDatabase.getInstance(application);
        list=db.getTechDao().getAllNews();
    }
    public LiveData<List<TechArticle>>getTechNews(){
        return list;
    }
    public void insert(List<TechArticle>list){
        new InsertList(db).execute(list);
    }
    public void delete(){
        new DeleteList(db).execute();
    }
    class InsertList extends AsyncTask<List<TechArticle>,Void,Void> {
        private TechDao dao;
        InsertList(NewsDatabase newsDatabase){
            dao=newsDatabase.getTechDao();
        }
        @Override
        protected Void doInBackground(List<TechArticle>... lists) {
            dao.insert(lists[0]);
            return null;
        }
    }
    class DeleteList extends AsyncTask<Void,Void,Void>{
      private TechDao dao;
      DeleteList(NewsDatabase newsDatabase){
          dao=newsDatabase.getTechDao();
      }
        @Override
        protected Void doInBackground(Void... voids) {
          dao.delete();
            return null;
        }
    }
}
