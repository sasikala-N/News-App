package com.example.news.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.news.Model.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    void insert(List<Article>list);
    @Query("Select * from article")
    LiveData<List<Article>>getAllNews();
    @Query("Delete from article")
    void delete();
}
