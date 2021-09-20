package com.example.mytestapp.question_two.room.builder

import android.content.Context
import androidx.room.Room
import com.example.mytestapp.question_two.room.database.RoundsDatabase

object DatabaseBuilder {

    private var dbInstance: RoundsDatabase? = null

    fun getDBInstance(context: Context):RoundsDatabase{
        if(dbInstance==null){
            synchronized(RoundsDatabase::class){
                dbInstance = buildRoundsDB(context)
            }
        }
        return dbInstance!!
    }

    private fun buildRoundsDB(context: Context): RoundsDatabase?{
        return Room.databaseBuilder(
            context.applicationContext,
            RoundsDatabase::class.java,
            "RoundsDB"
        ).build()
    }

}