package com.example.flogger.fragment

import android.hardware.SensorManager.getOrientation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
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
        // Set title bar
        (activity as MainActivity?)
            ?.setActionBarTitle("Flogger Fitness Logger")
        getRoutines()
    }

    private fun initAdapter() {
        routineAdapter = RoutineListAdapter(arrayListOf())

        val lm = LinearLayoutManager(context)

        routine_recyclerview.apply {
            layoutManager = lm
            adapter = routineAdapter
            addItemDecoration(DividerItemDecoration(context, lm.orientation))
        }

        itemTouchHelper.attachToRecyclerView(routine_recyclerview)

        routineAdapter.setEditOnClickListener {
            val action =
                RoutineListFragmentDirections
                    .actionRoutineListFragmentToEditRoutineFragment(it)
            view?.findNavController()?.navigate(action)
        }

        routineAdapter.setDeleteOnClickListener {
            routineViewModel.deleteRoutine(it)
        }

        routineAdapter.setNameOnClickListener {
            val action =
                RoutineListFragmentDirections
                    .actionRoutineListFragmentToViewRoutineFragment(it)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun initView() {

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        this.lifecycle.addObserver(routineViewModel)

        fab_add_routine.setOnClickListener {
            val action =
                RoutineListFragmentDirections
                    .actionRoutineListFragmentToAddRoutineFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    private fun getRoutines()
    {
        routineViewModel.routines.observe(this, Observer {
            routineAdapter.refreshAdapter(it)
        })
    }

    private val itemTouchHelper by lazy {
        // 1. Note that I am specifying all 4 directions.
        //    Specifying START and END also allows
        //    more organic dragging than just specifying UP and DOWN.
        val simpleItemTouchCallback =
            object : SimpleCallback(UP or
                    DOWN or
                    START or
                    END, 0) {

                override fun onMove(recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {

                    val adapter = recyclerView.adapter as RoutineListAdapter
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    // 2. Update the backing model. Custom implementation in
                    //    MainRecyclerViewAdapter. You need to implement
                    //    reordering of the backing model inside the method.
                    adapter.moveItem(from, to)
                    // 3. Tell adapter to render the model update.
                    adapter.notifyItemMoved(from, to)

                    return true
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                      direction: Int) {
                    // 4. Code block for horizontal swipe.
                    //    ItemTouchHelper handles horizontal swipe as well, but
                    //    it is not relevant with reordering. Ignoring here.
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }
}