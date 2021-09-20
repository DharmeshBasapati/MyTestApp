package com.example.mytestapp.question_two

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.R
import com.example.mytestapp.databinding.FragmentDashboardBinding
import com.example.mytestapp.question_two.room.builder.DatabaseBuilder
import com.example.mytestapp.question_two.room.database.RoundsDatabase
import com.example.mytestapp.question_two.room.entity.Rounds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private lateinit var dbdata: List<Rounds>
    private lateinit var dbInstance: RoundsDatabase
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)

        populateListWithRoundsList()

        return binding.root
    }

    private fun populateListWithRoundsList() {

        binding.rvRoundsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoundsList.addItemDecoration(DividerItemDecoration(activity?.baseContext,DividerItemDecoration.VERTICAL))

        //Init DB
        dbInstance = DatabaseBuilder.getDBInstance(this.requireContext())

        GlobalScope.launch(Dispatchers.IO) {
            dbdata = dbInstance.roundsDao().getAllRounds()
            Log.d(TimerFragment.TAG, "initDB: DBDATA = $dbdata")
        }.invokeOnCompletion {

            GlobalScope.launch(Dispatchers.Main){
                val roundsListAdapter = RoundsListAdapter(dbdata)

                binding.rvRoundsList.adapter = roundsListAdapter
            }
        }


    }

}