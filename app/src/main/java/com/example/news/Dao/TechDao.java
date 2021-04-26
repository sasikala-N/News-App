package com.example.news.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.news.Model.Article;
import com.example.news.Model.TechArticle;

import java.util.List;

@Dao
public interface TechDao {
    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(List<TechArticle> list);
    @Query("Select * from techarticle")
    LiveData<List<TechArticle>> getAllNews();
    @Query("Delete from techarticle")
    void delete();
}
