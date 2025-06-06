package com.example.worldclock

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.worldclock.databinding.FragmentAddCityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.TimeZone
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class AddCityFragment : Fragment() {
    private lateinit var binding: FragmentAddCityBinding
    private lateinit var db: AppDatabase

    fun testTimeZone(id: String) {
        val tz = TimeZone.getTimeZone(id)
        val time = SimpleDateFormat("HH:mm")
        time.timeZone = tz
        //println("$id -> ${time.format(Date())} | Offset: ${tz.rawOffset / 3600000}")
        Log.d("Mylog","$id -> ${time.format(Date())} | Offset: ${tz.rawOffset / 3600000}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())
        /*
        testTimeZone("GMT-07:00")  // GMT-07:00 -> 08:00 | Offset: -7
        testTimeZone("GMT-7")      // GMT-7 -> 15:00 | Offset: 0 (ошибка!)
        testTimeZone("UTC-07:00")  // UTC-07:00 -> 08:00 | Offset: -7
        testTimeZone("America/Los_Angeles") // -> 08:00 (или 09:00 если сейчас DST)
        */
        val customCities = listOf(
            "Moskow" to "Europe/Moscow",
            "Astana" to "Asia/Almaty",
            "Saint-Petersburg" to "Europe/Moscow",
            //"Seattle" to "GMT-7",
            "Seattle" to "America/Los_Angeles",
            "Salt Lake City" to "GMT-6",
            //"Issaquah" to "UTC-7:00"
            "Issaquah" to "GMT-7:00"
        )

        // Подготовка списка городов
        val timeZones = TimeZone.getAvailableIDs()
            .filter { it.contains("/") }
            .map { it.split("/").last() to it }

        // Объединяем и сортируем
        val allCities = (timeZones + customCities)
            .distinctBy { it.first } // Убираем дубли
            .sortedBy { it.first } // Сортируем по названию

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            //timeZones.map { it.first }
            allCities.map { it.first }
        )
        binding.actvCity.setAdapter(adapter)

        // Сохранение города
        binding.btnSave.setOnClickListener {
            val cityName = binding.actvCity.text.toString()
            //val timeZone = timeZones.find { it.first == cityName }?.second
            val timeZone = allCities.find { it.first == cityName }?.second
            if (timeZone != null) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        db.cityDao().insert(City(name = cityName, timeZone = timeZone))
                    }
                    findNavController().popBackStack()
                }
            }
        }
    }

}
