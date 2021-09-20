package com.example.mytestapp.question_two.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytestapp.question_two.room.dao.RoundsDao
import com.example.mytestapp.question_two.room.entity.Rounds

@Database(entities = [Rounds::class], version = 1)
abstract class RoundsDatabase: RoomDatabase() {

    abstract fun roundsDao(): RoundsDao

}