package com.salomao.soccernews.data.datalocal;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.salomao.soccernews.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}