package com.example.flogger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.adapter.RoutineListAdapter
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.RoutineViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineListFragment : Fragment() {

    private lateinit var routineAdapter: RoutineListAdapter
    private val routineViewModel: RoutineViewModel by viewModels()

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
        // Hacky way to ensure back arrow not displayed in this fragment
        (activity as MainActivity?)
            ?.setBackNavigation()
        getRoutines()
    }

    private fun initAdapter() {
        routineAdapter = RoutineListAdapter(arrayListOf())

        val lm = LinearLayoutManager(context)
        val recyclerViewRoutines = view?.findViewById<RecyclerView>(R.id.routine_recyclerview)

        recyclerViewRoutines?.apply {
            layoutManager = lm
            adapter = routineAdapter
            addItemDecoration(DividerItemDecoration(context, lm.orientation))
        }

        itemTouchHelper.attachToRecyclerView(recyclerViewRoutines)

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

        this.lifecycle.addObserver(routineViewModel)
        val fabAddRoutine = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_add_routine)

        fabAddRoutine?.setOnClickListener {
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

        var adapter: RoutineListAdapter

        var draggedFrom = -1
        var draggedTo = -1

        var fromRoutine: Routine = Routine(-1, "", -1)
        var fromDisplayOrder = 0
        var toRoutine: Routine = Routine(-1, "", -1)
        var toDisplayOrder = 0

        val simpleItemTouchCallback =
            object : SimpleCallback(UP or
                    DOWN or
                    START or
                    END, 0) {

                override fun onMove(recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {

                    adapter = recyclerView.adapter as RoutineListAdapter

                    draggedFrom = viewHolder.adapterPosition
                    draggedTo = target.adapterPosition
                    // 2. Update the backing model. Custom implementation in
                    //    MainRecyclerViewAdapter. You need to implement
                    //    reordering of the backing model inside the method.
                    adapter.moveItem(draggedFrom, draggedTo)
                    // 3. Tell adapter to render the model update.
                    adapter.notifyItemMoved(draggedFrom, draggedTo)

                    return true
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {

                    if (draggedFrom != -1 && draggedTo != -1 && draggedFrom != draggedTo)
                    {
                        moveCompleted(recyclerView)
                    }

                    draggedFrom = -1
                    draggedTo = -1

                    super.clearView(recyclerView, viewHolder)
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                      direction: Int) {
                    // 4. Code block for horizontal swipe.
                    //    ItemTouchHelper handles horizontal swipe as well, but
                    //    it is not relevant with reordering. Ignoring here.
                }

                private fun moveCompleted(recyclerView: RecyclerView) {

                    adapter = recyclerView.adapter as RoutineListAdapter

                    fromRoutine = adapter.getItemByPosition(draggedFrom)
                    fromDisplayOrder = fromRoutine.displayOrder
                    toRoutine = adapter.getItemByPosition(draggedTo)
                    toDisplayOrder = toRoutine.displayOrder

                    // 5. Update displayOrders of moved items in DB
                    routineViewModel.updateRoutine(Routine(fromRoutine.routineId, fromRoutine.name, toDisplayOrder))
                    routineViewModel.updateRoutine(Routine(toRoutine.routineId, toRoutine.name, fromDisplayOrder))
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }
}