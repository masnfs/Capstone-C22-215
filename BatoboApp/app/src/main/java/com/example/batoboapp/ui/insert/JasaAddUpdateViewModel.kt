package com.example.batoboapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.batoboapp.database.Jasa
import com.example.batoboapp.repository.JasaRepository

class JasaAddUpdateViewModel(application: Application) : ViewModel() {
    private val mJasaRepository: JasaRepository = JasaRepository(application)
    fun insert(jasa: Jasa) {
        mJasaRepository.insert(jasa)
    }
    fun update(jasa: Jasa) {
        mJasaRepository.update(jasa)
    }
    fun delete(jasa: Jasa) {
        mJasaRepository.delete(jasa)
    }
}