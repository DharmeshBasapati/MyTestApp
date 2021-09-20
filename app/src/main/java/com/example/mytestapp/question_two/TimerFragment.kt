package com.example.mytestapp.question_two

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestapp.R
import com.example.mytestapp.databinding.FragmentTimerBinding
import com.example.mytestapp.question_two.room.builder.DatabaseBuilder
import com.example.mytestapp.question_two.room.database.RoundsDatabase
import com.example.mytestapp.question_two.room.entity.Rounds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class TimerFragment : Fragment() {

    companion object {
        const val TIMER_INTERVAL = 1000
        const val TAG = "TimerFragment"
        const val DATE_TIME_FORMAT = "dd/M/yyyy hh:mm:ss a"
    }

    private lateinit var currentRoundAdapter: CurrentRoundAdapter
    private lateinit var dbInstance: RoundsDatabase
    private val mInterval = TIMER_INTERVAL
    private var mHandler: Handler? = null

    private var timeInSeconds = 0L
    private var startButtonClicked = false

    private var roundNum = 1
    private lateinit var currentRoundsList: ArrayList<Rounds>

    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(layoutInflater)

        initDB()

        initStopWatch()

        initCurrentRoundsList()

        enableStopButton(false)

        binding.btnStartTime.setOnClickListener {
            startPauseResumeButtonClicked()
        }

        binding.btnEndTime.setOnClickListener {
            stopTimer()
            resetTimerView()
            initStopWatch()
        }

        return binding.root
    }

    private fun enableStopButton(enable: Boolean) {
        binding.btnEndTime.isEnabled = enable
    }

    private fun initCurrentRoundsList() {

        currentRoundsList = ArrayList()

        binding.rvDateTimeList.layoutManager = LinearLayoutManager(requireContext())

        binding.rvDateTimeList.addItemDecoration(
            DividerItemDecoration(
                activity?.baseContext,
                DividerItemDecoration.VERTICAL
            )
        )

        currentRoundAdapter = CurrentRoundAdapter(arrayListOf())

        binding.rvDateTimeList.adapter = currentRoundAdapter

    }

    private fun initDB() {
        dbInstance = DatabaseBuilder.getDBInstance(this.requireContext())

        GlobalScope.launch(Dispatchers.IO) {
            roundNum = dbInstance.roundsDao().getLatestRoundNumber()
            roundNum++
            Log.d(TAG, "Latest Round Number: $roundNum")
        }.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.Main) {
                binding.tvRoundNumber.text = "Round $roundNum"
            }
        }
    }

    private fun addNewTimeToDB(timerTime: String, deviceTime: String, totalTime: String) {

        val round = Rounds(
            timerTime = timerTime,
            deviceTime = deviceTime,
            totalTime = totalTime,
            roundNumber = roundNum
        )

        GlobalScope.launch(Dispatchers.IO) {
            dbInstance.roundsDao().addNewTime(round)
        }

        currentRoundsList.add(round)

        updateCurrentRoundsList()

    }

    private fun updateCurrentRoundsList() {
        currentRoundAdapter.addCurrentTimeList(currentRoundsList)
        if (currentRoundsList.size > 0) {
            currentRoundAdapter.notifyItemInserted(currentRoundsList.size - 1)
        }
    }

    private fun initStopWatch() {
        binding.tvTimer.text = getString(R.string.label_init_time)
    }

    private fun resetTimerView() {

        timeInSeconds = 0
        startButtonClicked = false
        binding.btnStartTime.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )
        binding.btnStartTime.text = getString(R.string.label_start)
        initStopWatch()

    }

    private fun startPauseResumeButtonClicked() {
        if (!startButtonClicked) {
            startTimer()
            startTimerView()
        } else {
            pauseTimer()
            pauseTimerView()
        }
    }

    private fun startTimer() {
        mHandler = Handler(Looper.getMainLooper())
        mStatusChecker.run()
    }

    private fun pauseTimer() {

        addNewTimeToDB(
            timerTime = getFormattedStopWatch((timeInSeconds * 1000)),
            deviceTime = getCurrentDateAndTime(),
            totalTime = ""
        )
        mHandler?.removeCallbacks(mStatusChecker)

    }

    private fun stopTimer() {

        addNewTimeToDB(
            timerTime = getFormattedStopWatch((timeInSeconds * 1000)),
            deviceTime = getCurrentDateAndTime(),
            totalTime = getFormattedStopWatch((timeInSeconds * 1000))
        )

        Toast.makeText(requireContext(), "Round $roundNum Saved to Database.", Toast.LENGTH_SHORT)
            .show()

        mHandler?.removeCallbacks(mStatusChecker)
        currentRoundsList = ArrayList()

        roundNum++
        binding.tvRoundNumber.text = "Round $roundNum"

        updateCurrentRoundsList()
        enableStopButton(false)

    }

    private fun pauseTimerView() {
        binding.btnStartTime.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.teal_700
            )
        )
        binding.btnStartTime.text = getString(R.string.label_resume)
        startButtonClicked = false
    }

    private fun startTimerView() {

        binding.btnStartTime.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.design_default_color_error
            )
        )
        binding.btnStartTime.text = getString(R.string.label_pause)
        startButtonClicked = true
        enableStopButton(true)

    }

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                timeInSeconds += 1
                updateStopWatchView(timeInSeconds)
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }
    }

    private fun updateStopWatchView(timeInSeconds: Long) {
        val formattedTime = getFormattedStopWatch((timeInSeconds * 1000))
        binding.tvTimer.text = formattedTime
    }

    private fun getCurrentDateAndTime() =
        SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH).format(Date())


    private fun getFormattedStopWatch(ms: Long): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }
}