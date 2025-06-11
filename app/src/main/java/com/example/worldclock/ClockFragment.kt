package com.example.worldclock

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager //_
import com.example.worldclock.databinding.FragmentClockBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask
import com.example.worldclock.CustomAnalogClock

class ClockFragment : Fragment() {
    private lateinit var binding: FragmentClockBinding
    private lateinit var adapter: CitiesAdapter
    private lateinit var db: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentClockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())

        // Настройка RecyclerView
        adapter = CitiesAdapter { city ->
            lifecycleScope.launch {
                db.cityDao().delete(city)
                loadCities()
            }
        }
        binding.recyclerView.adapter = adapter

        //_Need for GridLayoutManager
        val layoutManager = GridLayoutManager(requireContext(), 2) // 3 - количество столбцов
        binding.recyclerView.layoutManager = layoutManager //

        // Need for LinearLayoutManager
        //binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        // Кнопка добавления города
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_clockFragment_to_addCityFragment)
        }

        // Обновление времени каждую минуту 60000 (секунду 1000)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }, 0, 60000) //1000

        loadCities()
    }


    private fun loadCities() {
        lifecycleScope.launch {
            db.cityDao().getAll().collect { cities ->
                adapter.updateCities(cities)
            }
        }
    }
}
