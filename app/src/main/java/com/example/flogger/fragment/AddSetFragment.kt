package com.example.flogger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.entity.Set
import com.example.flogger.enumeration.ExerciseType
import com.example.flogger.viewmodel.SetViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_set.*


@AndroidEntryPoint
class AddSetFragment : Fragment() {
    private val args: AddSetFragmentArgs by navArgs()
    private lateinit var setViewModel: SetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        // Set title bar
        (activity as MainActivity?)
            ?.setActionBarTitle("Add Set")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fabAddSet = view.findViewById<FloatingActionButton>(R.id.fab_add_set)


        initView()
    }

    private fun initView() {
        setViewModel = ViewModelProvider(this).get(SetViewModel::class.java)

        val exerciseTypeAdapter = this.context?.let { ArrayAdapter<ExerciseType>(
            it,
            R.layout.dropdown_exercise_type_item,
            ExerciseType.values()
        ) }

        filled_edp_exerciseType.setAdapter(exerciseTypeAdapter)

        fab_save_added_set.setOnClickListener {
            val set = getSetDetails()
            setViewModel.insertSet(set)
            val action =
                AddSetFragmentDirections
                    .actionAddSetFragmentToEditRoutineFragment(args.routine)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun getSetDetails() : Set {
        val performOrder = edittext_set_performOrder.text.toString().toInt()
        val exercise = edittext_set_exercise.text.toString()
        val exerciseType = ExerciseType.REP_BASED
        val goal = edittext_set_goal.text.toString().toInt()
        val routineId = args.routine.routineId

        return Set(
            0,
            performOrder,
            exercise,
            exerciseType,
            goal,
            routineId
        )
    }
}