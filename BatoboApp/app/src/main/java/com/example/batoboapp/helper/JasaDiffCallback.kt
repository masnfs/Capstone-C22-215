package com.example.batoboapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.batoboapp.database.Jasa

class JasaDiffCallback (private val mOldJasaList: List<Jasa>, private val mNewJasaList: List<Jasa>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldJasaList.size
    }
    override fun getNewListSize(): Int {
        return mNewJasaList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldJasaList[oldItemPosition].id == mNewJasaList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldJasaList[oldItemPosition]
        val newEmployee = mNewJasaList[newItemPosition]
        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
    }
}