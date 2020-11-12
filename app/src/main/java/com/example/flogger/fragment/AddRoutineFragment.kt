package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_routine.*

@AndroidEntryPoint
class AddRoutineFragment : Fragment() {

    private val routineViewModel: RoutineViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        // Set title bar
        (activity as MainActivity?)
            ?.setActionBarTitle("Add Routine")
        (activity as MainActivity?)
            ?.setBackNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_routine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.lifecycle.addObserver(routineViewModel)

        fab_add_routine.setOnClickListener {
            val routine = getRoutineDetails()
            routineViewModel.insertRoutine(routine)

            val action =
                AddRoutineFragmentDirections
                    .actionAddRoutineFragmentToRoutineListFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun getRoutineDetails(): Routine {
        val name = edittext_routine_name.text.toString()
        return Routine(0, name)
    }
}