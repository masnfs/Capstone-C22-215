package com.example.batoboapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.batoboapp.database.Jasa
import com.example.batoboapp.repository.JasaRepository

class ListKelompokViewModel(application: Application) : ViewModel() {
    private val mJasaRepository: JasaRepository = JasaRepository(application)

    fun getAllJasa(): LiveData<List<Jasa>> = mJasaRepository.getAllJasa()
    }