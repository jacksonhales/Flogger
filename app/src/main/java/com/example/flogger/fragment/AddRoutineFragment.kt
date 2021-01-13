package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.RoutineViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

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

        val fabAddRoutine = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_add_routine)

        fabAddRoutine.setOnClickListener {
            val routine = getRoutineDetails()
            routineViewModel.insertRoutine(routine)

            val action =
                AddRoutineFragmentDirections
                    .actionAddRoutineFragmentToRoutineListFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun getRoutineDetails(): Routine {

        val editTextRoutineName = view?.findViewById<EditText>(R.id.edittext_routine_name)
        val name = editTextRoutineName?.text.toString()

/*        routineViewModel.largestDisplayOrder.observe(viewLifecycleOwner,  {
            displayOrder = it!!
        })*/

        routineViewModel.getLargestRoutineDisplayOrder()

        val largestDisplayOrder = routineViewModel.largestDisplayOrder.value!!

        return Routine(0, name, largestDisplayOrder + 1)
    }
}