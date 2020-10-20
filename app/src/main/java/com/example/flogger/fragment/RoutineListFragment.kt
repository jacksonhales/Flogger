package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flogger.R
import com.example.flogger.adapter.RoutineListAdapter
import com.example.flogger.viewmodel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_routine_list.*

@AndroidEntryPoint
class RoutineListFragment : Fragment() {

    private lateinit var routineAdapter: RoutineListAdapter
    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initView()
    }

    override fun onResume() {
        super.onResume()
        getRoutines()
    }

    private fun initAdapter() {
        routineAdapter = RoutineListAdapter(arrayListOf())
        routine_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = routineAdapter
        }

        routineAdapter.setEditOnClickListener {
            val action =
                RoutineListFragmentDirections
                    .actionRoutineListFragmentToEditRoutineFragment(it)
            view?.findNavController()?.navigate(action)
        }

        routineAdapter.setDeleteOnClickListener {
            routineViewModel.delete(it)
        }
    }

    private fun initView() {

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        this.lifecycle.addObserver(routineViewModel)

        routine_add_fab.setOnClickListener {
            val action =
                RoutineListFragmentDirections
                    .actionRoutineListFragmentToAddRoutineFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    private fun getRoutines()
    {
        routineViewModel.routines.observe(this,  Observer {
            routineAdapter.refreshAdapter(it)
        })
    }



}