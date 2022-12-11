package com.example.batoboapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batoboapp.R
import com.example.batoboapp.databinding.ActivityListKelompokBinding
import com.example.batoboapp.helper.ViewModelFactory
import com.example.batoboapp.ui.insert.JasaAddUpdateActivity

class ListKelompokActivity : AppCompatActivity() {

    private var _activityListKelompokBinding: ActivityListKelompokBinding? = null
    private val binding get() = _activityListKelompokBinding
    private lateinit var adapter: JasaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityListKelompokBinding = ActivityListKelompokBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val listKelompokViewModel = obtainViewModel(this@ListKelompokActivity)
        listKelompokViewModel.getAllJasa().observe(this, { jasaList ->
            if (jasaList != null) {
                adapter.setListJasa(jasaList)
            }
        })

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@ListKelompokActivity, JasaAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }

        adapter = JasaAdapter()
        binding?.rvJasa?.layoutManager = LinearLayoutManager(this)
        binding?.rvJasa?.setHasFixedSize(true)
        binding?.rvJasa?.adapter = adapter

    }

    private fun obtainViewModel(activity: AppCompatActivity): ListKelompokViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ListKelompokViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityListKelompokBinding = null
    }
}