package com.example.worldclock

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AnalogClock
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import com.example.worldclock.CustomAnalogClock
/*
class CitiesAdapter(
    private val onDeleteClick: (City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val cities = mutableListOf<City>()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: City) {
            timeFormat.timeZone = TimeZone.getTimeZone(city.timeZone)
            itemView.apply {
                findViewById<TextView>(R.id.tvCityName).text = city.name
                findViewById<TextView>(R.id.tvTime).text = timeFormat.format(Date())
                findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
                    onDeleteClick(city)
                }
            }
        }
    }

    fun updateCities(newCities: List<City>) {
        cities.clear()
        cities.addAll(newCities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount() = cities.size
}
*/
class CitiesAdapter(
    private val onDeleteClick: (City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val cities = mutableListOf<City>()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        //private val analogClock: AnalogClock = itemView.findViewById(R.id.analogClock)
        private val analogClock: CustomAnalogClock = itemView.findViewById(R.id.analogClock)

        @RequiresApi(Build.VERSION_CODES.S)
        fun bind(city: City) {
            // Устанавливаем часовой пояс
            val timeZone = TimeZone.getTimeZone(city.timeZone)
            timeFormat.timeZone = timeZone

            // Обновляем текстовое время
            tvCityName.text = city.name
            tvTime.text = timeFormat.format(Date())

            // Настраиваем аналоговые часы (для API <23)
            try {
                // Способ 1: Через reflection (если стандартные методы недоступны)
                val method = analogClock::class.java.getMethod("setTimeZone", String::class.java)
                method.invoke(analogClock, city.timeZone)
            } catch (e: Exception) {
                // Способ 2: Альтернативная реализация для новых API
                //_analogClock.timeZone = city.timeZone
            }

            // Обработчик удаления
            btnDelete.setOnClickListener { onDeleteClick(city) }
        }
    }

    // Остальные методы остаются без изменений
    fun updateCities(newCities: List<City>) {
        cities.clear()
        cities.addAll(newCities)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount() = cities.size
}