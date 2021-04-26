package com.example.news.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.news.Model.AppleArticle;
import com.example.news.Model.Article;

import java.util.List;
@Dao
public interface AppleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AppleArticle>list);
    @Query("select * from applearticle")
    LiveData<List<AppleArticle>>getApplenews();
    @Query("delete from applearticle")
    void delete();
}
