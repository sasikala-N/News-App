package com.example.news.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TechResponceModel {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<TechArticle> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<TechArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<TechArticle> articles) {
        this.articles = articles;
    }
}
