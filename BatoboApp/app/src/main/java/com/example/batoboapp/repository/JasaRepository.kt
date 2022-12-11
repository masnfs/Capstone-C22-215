package com.example.batoboapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.batoboapp.database.Jasa
import com.example.batoboapp.database.JasaDao
import com.example.batoboapp.database.JasaRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JasaRepository(application: Application) {
    private val mJasaDao: JasaDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = JasaRoomDatabase.getDatabase(application)
        mJasaDao = db.jasaDao()
    }
    fun getAllJasa(): LiveData<List<Jasa>> = mJasaDao.getAllJasa()
    fun insert(jasa: Jasa) {
        executorService.execute { mJasaDao.insert(jasa) }
    }
    fun delete(jasa: Jasa) {
        executorService.execute { mJasaDao.delete(jasa) }
    }
    fun update(jasa: Jasa) {
        executorService.execute { mJasaDao.update(jasa) }
    }
}