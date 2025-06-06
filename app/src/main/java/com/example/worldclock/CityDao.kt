package com.example.worldclock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    // Добавление нового города
    @Insert
    suspend fun insert(city: City)

    // Удаление города
    @Delete
    suspend fun delete(city: City)

    // Получение всех городов (Flow для автоматического обновления UI)
    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getAll(): Flow<List<City>>

    // Поиск города по имени (пригодится для будущего поиска)
    @Query("SELECT * FROM cities WHERE name LIKE :query || '%'")
    suspend fun searchCities(query: String): List<City>
}
