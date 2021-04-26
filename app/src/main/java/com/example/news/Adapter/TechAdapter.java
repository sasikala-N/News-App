package com.example.news.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.Model.Article;
import com.example.news.Model.TechArticle;
import com.example.news.R;
import com.example.news.databinding.TechItemListBinding;

import java.util.List;

public class TechAdapter extends RecyclerView.Adapter<TechAdapter.TechViewHolder> {
    private List<TechArticle> articleList;
    Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    public TechAdapter(List<TechArticle> articleList, Context context) {
        this.articleList = articleList;
        this.context=context;
    }

    @NonNull
    @Override
    public TechAdapter.TechViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tech_item_list,parent,false);
        return new TechAdapter.TechViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechAdapter.TechViewHolder holder, int position) {
     final TechArticle article=articleList.get(position);
     if(!TextUtils.isEmpty(article.getTitle())){
     holder.binding.title.setText(article.getTitle());
     }
     if(!TextUtils.isEmpty(article.getUrlToImage())){
         Glide.with(context).load(article.getUrlToImage()).listener(new RequestListener<Drawable>() {
             @Override
             public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                 holder.binding.progress.setVisibility(View.GONE);
                 return false;
             }

             @Override
             public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                 holder.binding.progress.setVisibility(View.GONE);
                 return false;
             }
         }).into(holder.binding.techimg);
     }
     if(!TextUtils.isEmpty((article.getPublishedAt()))){
         holder.binding.postedat.setText(article.getPublishedAt());
     }
     if(!TextUtils.isEmpty(article.getDescription())){
         holder.binding.description.setText(article.getDescription());
     }
     holder.binding.technews.setTag(article);
    }
    public void getupdate(List<TechArticle>list){
        this.articleList=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class TechViewHolder extends RecyclerView.ViewHolder {
       TechItemListBinding binding;
        public TechViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TechItemListBinding.bind(itemView);
            binding.technews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(), v);
                    }
                }
            });
        }
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
