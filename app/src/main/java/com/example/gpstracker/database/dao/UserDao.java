package com.example.gpstracker.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gpstracker.database.entity.UserEntity;

@Dao
public interface UserDao {
    @Insert
    void insert(UserEntity obj);

    @Update
    void update(UserEntity obj);

    @Query("SELECT * FROM users WHERE login = :login ORDER BY id DESC LIMIT 1")
    UserEntity getUserByLogin(String login);

    @Query("SELECT * FROM users WHERE active = 1 ORDER BY id DESC LIMIT 1")
    UserEntity getActiveUser();
}
