package com.example.mytestapp.question_two.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rounds(
    @PrimaryKey(autoGenerate=true) val roundId: Int = 0,
    @ColumnInfo(name = "timerTime") val timerTime: String,
    @ColumnInfo(name = "deviceTime") val deviceTime: String,
    @ColumnInfo(name = "totalTime") val totalTime: String,
    @ColumnInfo(name = "roundNumber") val roundNumber: Int
)
