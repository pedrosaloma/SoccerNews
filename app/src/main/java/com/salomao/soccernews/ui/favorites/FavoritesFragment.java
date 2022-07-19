package com.salomao.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.salomao.soccernews.MainActivity;
import com.salomao.soccernews.databinding.FragmentFavoritesBinding;
import com.salomao.soccernews.domain.News;
import com.salomao.soccernews.ui.adapter.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel dashboardViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);



        findFavoriteNews();

        return binding.getRoot();
    }

    private void findFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
        List<News> favoriteNews = null;
        if (activity != null) {
            favoriteNews = activity.getDb().newsDao().loadFavoriteNews();
            binding.ReciclerViewFavoriteNews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.ReciclerViewFavoriteNews.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
                activity.getDb().newsDao().save(updatedNews);
                findFavoriteNews();
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}