package com.example.mytestapp.question_two

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.databinding.RowItemRoundsListBinding
import com.example.mytestapp.question_two.room.entity.Rounds

class RoundsListAdapter(var roundsList: List<Rounds>) :
    RecyclerView.Adapter<RoundsListAdapter.RoundsViewHolder>() {

    inner class RoundsViewHolder(val rowItemRoundsListBinding: RowItemRoundsListBinding) :
        RecyclerView.ViewHolder(rowItemRoundsListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RoundsViewHolder(
        RowItemRoundsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RoundsViewHolder, position: Int) {

        holder.rowItemRoundsListBinding.tvRoundNumber.text =
            holder.itemView.context.getString(R.string.label_round_number,
                { roundsList[position].roundNumber })

        if (position == 0) {
            holder.rowItemRoundsListBinding.lnrRoundsHeader.visibility = View.VISIBLE
        } else {
            if (roundsList[position].roundNumber != roundsList[position - 1].roundNumber) {
                holder.rowItemRoundsListBinding.lnrRoundsHeader.visibility = View.VISIBLE
            } else {
                holder.rowItemRoundsListBinding.lnrRoundsHeader.visibility = View.GONE
            }
        }

        holder.rowItemRoundsListBinding.tvDeviceTime.text = roundsList[position].deviceTime
        holder.rowItemRoundsListBinding.tvTimerTime.text = roundsList[position].timerTime

    }

    override fun getItemCount() = roundsList.size

}