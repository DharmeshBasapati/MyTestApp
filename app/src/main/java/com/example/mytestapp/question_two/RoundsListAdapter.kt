package com.example.mytestapp.question_two

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.question_two.room.entity.Rounds
import com.google.android.material.textview.MaterialTextView

class RoundsListAdapter(var roundsList: List<Rounds>) :
    RecyclerView.Adapter<RoundsListAdapter.RoundsViewHolder>() {

    class RoundsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceTime: MaterialTextView = itemView.findViewById(R.id.tvDeviceTime)
        val tvTimerTime: MaterialTextView = itemView.findViewById(R.id.tvTimerTime)
        val tvRoundNumber: MaterialTextView = itemView.findViewById(R.id.tvRoundNumber)
        val tvTotalTime: MaterialTextView = itemView.findViewById(R.id.tvTotalTime)
        val lnrRoundsHeader: LinearLayout = itemView.findViewById(R.id.lnrRoundsHeader)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundsViewHolder {
        return RoundsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_rounds_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RoundsViewHolder, position: Int) {

        holder.tvRoundNumber.text = "Round ${roundsList[position].roundNumber}"

        if (position == 0) {
            holder.lnrRoundsHeader.visibility = View.VISIBLE
        } else {
            if (roundsList[position].roundNumber != roundsList[position - 1].roundNumber) {
                holder.lnrRoundsHeader.visibility = View.VISIBLE
            } else {
                holder.lnrRoundsHeader.visibility = View.GONE
            }
        }

        holder.tvDeviceTime.text = roundsList[position].deviceTime
        holder.tvTimerTime.text = roundsList[position].timerTime

    }

    override fun getItemCount(): Int {
        return roundsList.size
    }

}