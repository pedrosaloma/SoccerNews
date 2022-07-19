package com.salomao.soccernews.data;

import com.salomao.soccernews.domain.News;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SoccerNewsAPI {

    @GET("news.json")
    Call<List<News>> getNews();
}
