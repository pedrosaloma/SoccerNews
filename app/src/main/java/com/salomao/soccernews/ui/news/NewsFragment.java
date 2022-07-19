package com.salomao.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.salomao.soccernews.MainActivity;
import com.salomao.soccernews.data.datalocal.AppDatabase;
import com.salomao.soccernews.databinding.FragmentNewsBinding;
import com.salomao.soccernews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel NewsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.RecicleViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshNews(NewsViewModel);

        NewsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state){
                case INPROGRESS:
                    binding.SwipeRefreshLayoutNews.setRefreshing(true);
                    break;
                case DONE:
                    binding.SwipeRefreshLayoutNews.setRefreshing(false);
                    break;
                case ERROR:
                    binding.SwipeRefreshLayoutNews.setRefreshing(false);
                    Snackbar.make(binding.SwipeRefreshLayoutNews, "Erro de conexão", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.SwipeRefreshLayoutNews.setOnRefreshListener(() -> {
            NewsViewModel.findNews();
        });

        return root;
    }

    private void refreshNews(NewsViewModel NewsViewModel) {
        NewsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.RecicleViewNews.setAdapter(new NewsAdapter(news, updatedNews-> { // updatedNews foi criado diretamente neste método
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.getDb().newsDao().save(updatedNews);
                }
            }));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

