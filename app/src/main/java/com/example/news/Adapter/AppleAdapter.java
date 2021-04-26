package com.example.news.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.Model.AppleArticle;
import com.example.news.Model.Article;
import com.example.news.R;
import com.example.news.databinding.AppleItemListBinding;

import java.util.List;

public class AppleAdapter extends RecyclerView.Adapter<AppleAdapter.ViewHolder> {
    private List<AppleArticle> articleArrayList;
    private Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public AppleAdapter(List<AppleArticle> articleArrayList, Context context) {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.apple_item_list,parent,false);
       return new AppleAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     final AppleArticle appleArticle=articleArrayList.get(position);
        if (!TextUtils.isEmpty(appleArticle.getTitle())) {
            holder.binding.atitle.setText(appleArticle.getTitle());
        }
        if (!TextUtils.isEmpty(appleArticle.getDescription())) {
            holder.binding.adescription.setText(appleArticle.getDescription());
        }
        if (!TextUtils.isEmpty(appleArticle.getUrlToImage())) {
            Glide.with(context).load(appleArticle.getUrlToImage()).listener(new RequestListener<Drawable>() {
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
            }).into(holder.binding.aimg);
        }
        if (!TextUtils.isEmpty(appleArticle.getPublishedAt())) {
            holder.binding.apostedAt.setText(appleArticle.getPublishedAt());
        }
        holder.binding.applenews.setTag(appleArticle);
    }
    public void getupdate(List<AppleArticle>list){
        this.articleArrayList=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        AppleItemListBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=AppleItemListBinding.bind(itemView);
            binding.applenews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecyclerViewItemClickListener !=null){
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(),v);
                    }
                }
            });
        }
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener =onRecyclerViewItemClickListener;
    }
}
