package com.example.mytestapp.question_one

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.databinding.RowItemTimeListBinding

class TimeListAdapter(val adapterOnClick: (Any) -> Unit, private var timeList: ArrayList<String>) :
    RecyclerView.Adapter<TimeListAdapter.TimeListViewHolder>() {

    inner class TimeListViewHolder(val rowItemTimeListBinding: RowItemTimeListBinding) :
        RecyclerView.ViewHolder(rowItemTimeListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TimeListViewHolder(
        RowItemTimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: TimeListViewHolder, position: Int) {
        holder.rowItemTimeListBinding.tvTimeFrame.text = timeList[position]
        holder.rowItemTimeListBinding.tvTimeFrame.setOnClickListener {
            adapterOnClick("YOU CLICKED - ${timeList[position]}")
        }
    }

    override fun getItemCount(): Int {
        return this.timeList.size
    }

    fun addTimeList(_timeList: ArrayList<String>) {
        this.timeList = _timeList
    }
}