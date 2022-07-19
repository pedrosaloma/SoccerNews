package com.salomao.soccernews.data.datalocal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.salomao.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {

        @Query("SELECT * FROM news WHERE favorite = 1")
        List<News> loadFavoriteNews();


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void save(News news);
}
