package com.example.flogger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.adapter.SetListAdapter
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.RoutineViewModel
import com.example.flogger.viewmodel.SetViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditRoutineFragment() : Fragment() {
    private val args: EditRoutineFragmentArgs by navArgs()
    private lateinit var setAdapter: SetListAdapter
    private var passedRoutine: Routine? = null
    private var updatedRoutine: Routine? = null
    private val routineViewModel: RoutineViewModel by viewModels()
    private val setViewModel: SetViewModel by viewModels()

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
        return inflater.inflate(R.layout.fragment_edit_routine, container, false)
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
            ?.setActionBarTitle("Edit Routine")
        (activity as MainActivity?)
            ?.setBackNavigation()

        passedRoutine?.routineId?.let { setViewModel.getSets(it) }
        getSets()
    }

    private fun initAdapter() {
        setAdapter = SetListAdapter(arrayListOf())
        val lm = LinearLayoutManager(context)
        val setListAdapter = view?.findViewById<RecyclerView>(R.id.set_recyclerview)

        setListAdapter?.apply {
            layoutManager = lm
            adapter = setAdapter
            addItemDecoration(DividerItemDecoration(context, lm.orientation))
        }

        itemTouchHelper.attachToRecyclerView(setListAdapter)

        setAdapter.setEditOnClickListener {
            val action =
                passedRoutine?.let { it1 ->
                    EditRoutineFragmentDirections
                        .actionEditRoutineFragmentToEditSetFragment(it, it1)
                }
            if (action != null) {
                view?.findNavController()?.navigate(action)
            }
        }

        setAdapter.setDeleteOnClickListener {
            setViewModel.deleteSet(it)
        }
    }

    private fun initView() {
        passedRoutine = args.routine

        val editTextRoutineName = view?.findViewById<EditText>(R.id.edittext_routine_name)
        val fabAddSet = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_add_set)
        val fabUpdateRoutine = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_update_routine)

        editTextRoutineName?.setText(passedRoutine?.name)

        fabAddSet?.setOnClickListener {
            val action =
                EditRoutineFragmentDirections
                    .actionEditRoutineFragmentToAddSetFragment(passedRoutine!!)
            view?.findNavController()?.navigate(action)
        }

        fabUpdateRoutine?.setOnClickListener {
            updatedRoutine = Routine(
                passedRoutine!!.routineId,
                editTextRoutineName?.text.toString(),
                passedRoutine!!.displayOrder
            )

            routineViewModel.updateRoutine(updatedRoutine!!)
            val action =
                EditRoutineFragmentDirections
                    .actionEditRoutineFragmentToRoutineListFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    private fun getSets()
    {
        setViewModel.sets.observe(this, Observer {
            setAdapter.refreshAdapter(it)
        })
    }

    private val itemTouchHelper by lazy {
        // 1. Note that I am specifying all 4 directions.
        //    Specifying START and END also allows
        //    more organic dragging than just specifying UP and DOWN.
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or
                        ItemTouchHelper.DOWN or
                        ItemTouchHelper.START or
                        ItemTouchHelper.END, 0) {

                override fun onMove(recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    target: RecyclerView.ViewHolder): Boolean {

                    val adapter = recyclerView.adapter as SetListAdapter
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