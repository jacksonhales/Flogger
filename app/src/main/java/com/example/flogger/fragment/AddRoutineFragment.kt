package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.flogger.R
import com.example.flogger.entity.Routine
import com.example.flogger.viewmodel.RoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_routine.*

@AndroidEntryPoint
class AddRoutineFragment : Fragment() {

    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        this.lifecycle.addObserver(routineViewModel)

        add_routine.setOnClickListener {
            val routine = getNewRoutineDetails()
            routineViewModel.insert(routine)

            val action =
                AddRoutineFragmentDirections
                    .actionAddRoutineFragmentToRoutineListFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun getNewRoutineDetails(): Routine {
        val name = routine_name.text.toString()
        return Routine(0, name)
    }
}