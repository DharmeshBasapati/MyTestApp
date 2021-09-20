package com.example.mytestapp.question_one

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestapp.R
import com.example.mytestapp.databinding.FragmentTimerCalculationBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.collections.ArrayList

class TimerCalculationFragment : Fragment() {

    private var endTimeSelected: Boolean = false
    private var startTimeSelected: Boolean = false
    private lateinit var timeListAdapter: TimeListAdapter
    private lateinit var binding: FragmentTimerCalculationBinding
    private var startHour: Int = 0
    private var startMinute: Int = 0
    private var endHour: Int = 0
    private var endMinute: Int = 0
    private lateinit var timeList: ArrayList<String>

    private val TAG = "TimerCalculationFragmen"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerCalculationBinding.inflate(layoutInflater)

        binding.btnStartTime.setOnClickListener {
            selectStartTime()
        }

        binding.btnEndTime.setOnClickListener {
            selectEndTime()
        }

        binding.btnGo.setOnClickListener {
            generateTimeList()
        }

        initList()

        return binding.root
    }

    private fun selectStartTime() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("Select Start Time")
                .build()
        picker.show(parentFragmentManager, "START_TIME")
        picker.addOnPositiveButtonClickListener {
            Log.d(TAG, "selectStartTime: Hour - ${picker.hour} / Minute - ${picker.minute}")
            startTimeSelected = true
            startHour = picker.hour
            startMinute = picker.minute
            binding.tvStartTime.text =
                "Start Time - ${getModifiedHour(startHour)}:${getModifiedMinutes(startMinute)}"
        }

    }


    private fun selectEndTime() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("Select End Time")
                .build()
        picker.show(parentFragmentManager, "END_TIME")
        picker.addOnPositiveButtonClickListener {
            Log.d(TAG, "selectEndTime: Hour - ${picker.hour} / Minute - ${picker.minute}")
            val startTime = Calendar.getInstance()
            startTime.set(Calendar.HOUR, startHour)
            startTime.set(Calendar.MINUTE, startMinute)

            val endTime = Calendar.getInstance()
            endTime.set(Calendar.HOUR, picker.hour)
            endTime.set(Calendar.MINUTE, picker.minute)

            if (endTime.before(startTime)) {
                endTimeSelected = false
                Toast.makeText(
                    activity,
                    getString(R.string.msg_please_select_end_time_more_than_start_time),
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                endTimeSelected = true

                endHour = picker.hour
                endMinute = picker.minute

                binding.tvEndTime.text =
                    "End Time - ${getModifiedHour(endHour)}:${getModifiedMinutes(endMinute)}"
            }
        }
    }

    private fun generateTimeList() {

        if (startTimeSelected && endTimeSelected) {

            timeList = ArrayList<String>()

            Log.d(TAG, "generateTimeList: START TIME - $startHour:$startMinute")
            Log.d(TAG, "generateTimeList: END TIME - $endHour:$endMinute")

            //Create a time list between start and end time
            if (startHour == endHour && startMinute == endMinute) {
                //then show same time selected
                Toast.makeText(
                    activity,
                    getString(R.string.msg_you_selected_same_time),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (startHour == endHour) {
                //This is working properly
                //if same hour
                //then calculate minutes diff
                for (min in startMinute..endMinute) {
                    Log.d(TAG, "generateTimeList: $startHour:$min")
                    val time = "${getModifiedHour(startHour)}:${getModifiedMinutes(min)}"
                    timeList.add(time)
                }

            } else if (startHour < endHour) {
                //Logic remaining for this condition
                //st - 2:20
                //et - 14:25
                for (hour in startHour..endHour) {

                    for (minutes in startMinute..endMinute) {

                        Log.d(TAG, "generateTimeList: $hour:$minutes")

                    }

                }

            }

            updateTimeList()
            findUniqueNumbersFromTheList()

        } else {
            Toast.makeText(
                activity,
                getString(R.string.msg_please_select_both_first),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getModifiedHour(hour: Int): String {
        var modifiedStartHour = "" + hour
        if (hour < 10) {
            modifiedStartHour = "0$hour"
        }
        return modifiedStartHour
    }


    private fun getModifiedMinutes(min: Int): String {
        var modifiedMin = "" + min
        if (min < 10) {
            modifiedMin = "0$min"
        }
        return modifiedMin
    }

    private fun findUniqueNumbersFromTheList() {
        //TODO
    }

    private fun initList() {
        binding.rvTimeList.layoutManager = LinearLayoutManager(activity)
        timeListAdapter = TimeListAdapter(arrayListOf())
        binding.rvTimeList.adapter = timeListAdapter
    }

    private fun updateTimeList() {
        timeListAdapter.addTimeList(timeList)
        timeListAdapter.notifyDataSetChanged()
    }


}