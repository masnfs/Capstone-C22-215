package com.example.batoboapp.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.batoboapp.databinding.ItemKelompokBinding
import com.example.batoboapp.database.Jasa
import com.example.batoboapp.helper.JasaDiffCallback
import com.example.batoboapp.ui.insert.JasaAddUpdateActivity

class JasaAdapter: RecyclerView.Adapter<JasaAdapter.JasaViewHolder>() {
    private val listJasa = ArrayList<Jasa>()
    fun setListJasa(listJasa: List<Jasa>) {
        val diffCallback = JasaDiffCallback(this.listJasa, listJasa)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listJasa.clear()
        this.listJasa.addAll(listJasa)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JasaViewHolder {
        val binding = ItemKelompokBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JasaViewHolder(binding)
    }
    override fun onBindViewHolder(holder: JasaViewHolder, position: Int) {
        holder.bind(listJasa[position])
    }
    override fun getItemCount(): Int {
        return listJasa.size
    }
    inner class JasaViewHolder(private val binding: ItemKelompokBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jasa: Jasa) {
            with(binding) {
                // Set To Avatar Sample IMG
                // imgAvatar.setImageResource()
                tvItemTitle.text = jasa.title
                // tvItemDescription.text = jasa.description
                tvItemPhone.text = jasa.phone
                // tvItemDate.text = jasa.date
                cvItemJasa.setOnClickListener {
                    val intent = Intent(it.context, JasaAddUpdateActivity::class.java)
                    intent.putExtra(JasaAddUpdateActivity.EXTRA_JASA, jasa)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}