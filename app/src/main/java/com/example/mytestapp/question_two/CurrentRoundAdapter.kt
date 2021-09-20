package com.example.mytestapp.question_two

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.question_two.room.entity.Rounds
import com.google.android.material.textview.MaterialTextView

class CurrentRoundAdapter(var currentTimeList: ArrayList<Rounds>) :
    RecyclerView.Adapter<CurrentRoundAdapter.CRViewHolder>() {

    class CRViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceTime: MaterialTextView = itemView.findViewById(R.id.tvDeviceTime)
        val tvTimerTime: MaterialTextView = itemView.findViewById(R.id.tvTimerTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CRViewHolder {
        return CRViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_date_time_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CRViewHolder, position: Int) {
        holder.tvDeviceTime.text = currentTimeList[position].deviceTime
        holder.tvTimerTime.text = currentTimeList[position].timerTime
    }

    override fun getItemCount(): Int {
        return currentTimeList.size
    }

    fun addCurrentTimeList(_currentTimeList: ArrayList<Rounds>) {
        currentTimeList = _currentTimeList
    }
}