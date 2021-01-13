package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.adapter.SetListViewOnlyAdapter
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.SetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewRoutineFragment : Fragment() {

    private val args: ViewRoutineFragmentArgs by navArgs()
    private lateinit var setAdapter: SetListViewOnlyAdapter
    private var passedRoutine: Routine? = null
    private val setViewModel: SetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)
            ?.setActionBarTitle("View Routine")
        (activity as MainActivity?)
            ?.setBackNavigation()

        passedRoutine?.routineId?.let { setViewModel.getSets(it) }
        getSets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_routine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initView()
    }


    private fun initAdapter() {
        setAdapter = SetListViewOnlyAdapter(arrayListOf())
        val lm = LinearLayoutManager(context)
        val recyclerViewSetsViewOnly = view?.findViewById<RecyclerView>(R.id.set_view_only_recyclerview)
        recyclerViewSetsViewOnly?.apply {
            layoutManager = lm
            adapter = setAdapter
            addItemDecoration(DividerItemDecoration(context, lm.orientation))
        }
    }

    private fun initView() {
        passedRoutine = args.routine

        val textViewRoutineName = view?.findViewById<TextView>(R.id.textview_routine_name)

        textViewRoutineName?.text = passedRoutine!!.name
    }

    private fun getSets()
    {
        setViewModel.sets.observe(this, Observer {
            setAdapter.refreshAdapter(it)
        })
    }

}