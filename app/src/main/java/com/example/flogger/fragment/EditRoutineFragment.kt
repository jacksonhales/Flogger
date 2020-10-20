package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flogger.R
import com.example.flogger.adapter.SetListAdapter
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.EditRoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_routine.routine_name
import kotlinx.android.synthetic.main.fragment_edit_routine.*

@AndroidEntryPoint
class EditRoutineFragment() : Fragment() {
    private val args: EditRoutineFragmentArgs by navArgs()
    private lateinit var setAdapter: SetListAdapter
    private var routine: Routine? = null
    private lateinit var editRoutineViewModel: EditRoutineViewModel

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
        routine?.routineId?.let { editRoutineViewModel.getSets(it) }
        getSets()
    }

    private fun initAdapter() {
        setAdapter = SetListAdapter(arrayListOf())
        set_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = setAdapter
        }

        setAdapter.setEditOnClickListener {
            val action =
                EditRoutineFragmentDirections
                    .actionEditRoutineFragmentToEditSetFragment(it)
            view?.findNavController()?.navigate(action)
        }

        setAdapter.setDeleteOnClickListener {
            editRoutineViewModel.deleteSet(it)
        }
    }

    private fun initView() {
        editRoutineViewModel = ViewModelProvider(this).get(EditRoutineViewModel::class.java)

        routine = args.routine

        routine_name.setText(routine?.name)
    }

    private fun getSets()
    {
        editRoutineViewModel.sets.observe(this,  Observer {
            setAdapter.refreshAdapter(it)
        })
    }

}