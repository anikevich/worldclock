package com.example.worldclock

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cities")
data class City(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,          // Название города ("Нью-Йорк")
    val timeZone: String       // Часовой пояс ("America/New_York")
)
