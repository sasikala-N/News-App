package com.example.news.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.Model.Article;
import com.example.news.R;
import com.example.news.databinding.BusinessItemListBinding;

import java.util.List;

public class BusinessNewsAdapter extends RecyclerView.Adapter<BusinessNewsAdapter.ViewHolder> {
    private List<Article> articleArrayList;
    private Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public BusinessNewsAdapter(List<Article> articleArrayList, Context context) {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.business_item_list, viewGroup, false);
        return new BusinessNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Article articleModel = articleArrayList.get(position);
        if (!TextUtils.isEmpty(articleModel.getTitle())) {
            viewHolder.binding.Btitle.setText(articleModel.getTitle());
        }
        if (!TextUtils.isEmpty(articleModel.getDescription())) {
            viewHolder.binding.Bdescription.setText(articleModel.getDescription());
        }
        if (!TextUtils.isEmpty(articleModel.getUrlToImage())) {
            Glide.with(context).load(articleModel.getUrlToImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    viewHolder.binding.progress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                   viewHolder.binding.progress.setVisibility(View.GONE);
                    return false;
                }
            }).into(viewHolder.binding.techimgg);
        }
        if (!TextUtils.isEmpty(articleModel.getPublishedAt())) {
            viewHolder.binding.BpostedAt.setText(articleModel.getPublishedAt());
        }
        viewHolder.binding.Businessnews.setTag(articleModel);
    }
    public void getupdate(List<Article>list){
     this.articleArrayList=list;
     notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        BusinessItemListBinding binding;
       public ViewHolder(View view) {
            super(view);
            binding = BusinessItemListBinding.bind(view);
            binding.Businessnews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}