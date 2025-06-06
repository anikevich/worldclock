package com.example.worldclock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
