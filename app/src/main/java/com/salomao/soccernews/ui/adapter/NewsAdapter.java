package com.salomao.soccernews.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salomao.soccernews.R;
import com.salomao.soccernews.databinding.NewsItemBinding;
import com.salomao.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<News> dataNews;
    private final NewsListener favoriteListener;

    public NewsAdapter(List<News> dataNews, NewsListener favoriteListener) {
        this.dataNews = dataNews;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       News news = dataNews.get(position);
       Picasso.get().load(news.getImagem()).fit().into( holder.binding.ImageViewNews);
       holder.binding.TextViewTitle.setText(news.getTitle());
       holder.binding.textViewCorpo.setText(news.getCorpo());
       // Abrir link
       holder.binding.button.setOnClickListener(view -> {
           Intent i = new Intent(Intent.ACTION_VIEW);
           i.setData(Uri.parse(news.getLink()));
           holder.itemView.getContext().startActivity(i);
       });
       // Compartilhar
        holder.binding.IconShare.setOnClickListener(view -> {
            Intent i2 = new Intent(Intent.ACTION_SEND);
            i2.setType("text/plan");
            i2.putExtra(Intent.EXTRA_SUBJECT, news.getTitle());
            i2.putExtra(Intent.EXTRA_TEXT, news.getLink());
            holder.itemView.getContext().startActivity(Intent.createChooser(i2,"Share via"));
        });
        // Favoritar
        holder.binding.IconFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });
        if(news.favorite){
            holder.binding.IconFavorite.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.red));
        }else {
            holder.binding.IconFavorite.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.icon_default));
        }
    }

    @Override
    public int getItemCount() {
        return this.dataNews.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface NewsListener{
       void onFavorite(News news);
    }
}
