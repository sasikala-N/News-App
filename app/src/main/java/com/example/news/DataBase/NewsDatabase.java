package com.example.news.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.news.AppleActivity;
//import com.example.news.Dao.AppleDao;
import com.example.news.Dao.AppleDao;
import com.example.news.Dao.ArticleDao;
import com.example.news.Dao.TechDao;
import com.example.news.Model.AppleArticle;
import com.example.news.Model.Article;
import com.example.news.Model.TechArticle;

@Database(entities = {Article.class, TechArticle.class, AppleArticle.class},version = 8,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String Database_Name="NewsDatabase";
    private static volatile NewsDatabase INSTANCE;
    private static Context activity;
    public abstract ArticleDao getArticleDao();
    public abstract TechDao getTechDao();
    public abstract AppleDao getApplDao();
    public static NewsDatabase getInstance(Context context){
        activity=context.getApplicationContext();
     if(INSTANCE==null){
    synchronized (NewsDatabase.class){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context,NewsDatabase.class,Database_Name)
                    .addCallback(callback)
                    .build();
        }
       }
     }
     return INSTANCE;
    }
static Callback callback=new Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new Papulate(INSTANCE).execute();

    }
};
public static class Papulate extends AsyncTask<Void,Void,Void>{
    private ArticleDao dao;
    private TechDao tdao;
    private AppleDao adao;
    Papulate(NewsDatabase newsDatabase){
        dao=newsDatabase.getArticleDao();
        tdao=newsDatabase.getTechDao();
        adao=newsDatabase.getApplDao();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        tdao.delete();
        dao.delete();
        adao.delete();
        return null;
    }
}
}
