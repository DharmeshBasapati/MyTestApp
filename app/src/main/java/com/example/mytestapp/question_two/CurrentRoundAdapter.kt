package com.example.mytestapp.question_two

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.databinding.RowItemDateTimeListBinding
import com.example.mytestapp.question_two.room.entity.Rounds

class CurrentRoundAdapter(var currentTimeList: ArrayList<Rounds>) :
    RecyclerView.Adapter<CurrentRoundAdapter.CRViewHolder>() {

    inner class CRViewHolder(val rowItemDateTimeListBinding: RowItemDateTimeListBinding) :
        RecyclerView.ViewHolder(rowItemDateTimeListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CRViewHolder(
        RowItemDateTimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CRViewHolder, position: Int) {
        holder.rowItemDateTimeListBinding.tvDeviceTime.text = currentTimeList[position].deviceTime
        holder.rowItemDateTimeListBinding.tvTimerTime.text = currentTimeList[position].timerTime
    }

    override fun getItemCount() = currentTimeList.size

    fun addCurrentTimeList(_currentTimeList: ArrayList<Rounds>) {
        currentTimeList = _currentTimeList
    }
}