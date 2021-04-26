package com.example.news.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppleResponseModel {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<AppleArticle> articles = null;
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

    public List<AppleArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<AppleArticle> articles) {
        this.articles = articles;
    }
}
