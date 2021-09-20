package com.example.mytestapp.question_one

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.databinding.RowItemTimeListBinding
import com.google.android.material.textview.MaterialTextView

class TimeListAdapter(var timeList:ArrayList<String>): RecyclerView.Adapter<TimeListAdapter.TimeListViewHolder>() {

    class TimeListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val tvTimeFrame: MaterialTextView = itemView.findViewById(R.id.tvTimeFrame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeListViewHolder {
        return TimeListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item_time_list,parent,false))
    }

    override fun onBindViewHolder(holder: TimeListViewHolder, position: Int) {
        holder.tvTimeFrame.text = timeList[position]
    }

    override fun getItemCount(): Int {
        return this.timeList.size
    }

    fun addTimeList(_timeList:ArrayList<String>){
        this.timeList = _timeList
    }
}