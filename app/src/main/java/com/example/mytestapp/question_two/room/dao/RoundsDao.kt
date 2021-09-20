package com.example.mytestapp.question_two.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mytestapp.question_two.room.entity.Rounds

@Dao
interface RoundsDao {

    @Query("Select * from Rounds")
    fun getAllRounds(): List<Rounds>

    @Query("Select * from Rounds where Rounds.roundNumber = :roundNum")
    fun getParticularRoundsList(roundNum: Int): List<Rounds>

    @Insert
    suspend fun addNewTime(rounds: Rounds)

    @Query("Select max(roundNumber) from Rounds")
    fun getLatestRoundNumber(): Int
}