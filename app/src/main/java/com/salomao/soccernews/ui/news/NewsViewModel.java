package com.salomao.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.salomao.soccernews.data.SoccerNewsAPI;
import com.salomao.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    public enum State{
        INPROGRESS, DONE, ERROR
    }

    private final MutableLiveData<List<News>> mnews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();
    private final SoccerNewsAPI api;


    public NewsViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pedrosaloma.github.io/soccer_news_api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SoccerNewsAPI.class);

        this.findNews();


    }

    public void findNews() {
        state.setValue(State.INPROGRESS);
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    mnews.setValue(response.body());
                    state.setValue(State.DONE);
                }else{
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                state.setValue(State.ERROR);
            }
        });
    }

    public LiveData<List<News>> getNews() {return mnews;} // usado para observar
    public LiveData<State> getState() {return state;} // usado para observar

}