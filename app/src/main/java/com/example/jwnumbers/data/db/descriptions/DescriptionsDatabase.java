package com.example.jwnumbers.data.db.descriptions;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Description.class}, version = 1)
public abstract class DescriptionsDatabase extends RoomDatabase {
    public abstract DescriptionDao descriptionDao();
}