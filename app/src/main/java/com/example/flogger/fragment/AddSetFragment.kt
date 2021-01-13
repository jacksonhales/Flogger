package com.example.flogger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.entity.Set
import com.example.flogger.enumeration.ExerciseType
import com.example.flogger.viewmodel.SetViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddSetFragment : Fragment() {
    private val args: AddSetFragmentArgs by navArgs()
    private val setViewModel: SetViewModel by viewModels()

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

        initView()
    }

    private fun initView() {

        val exerciseTypeAdapter = this.context?.let { ArrayAdapter<ExerciseType>(
            it,
            R.layout.dropdown_exercise_type_item,
            ExerciseType.values()
        ) }

        val filledEDPExerciseType = view?.findViewById<AutoCompleteTextView>(R.id.filled_edp_exerciseType)
        val fabSaveAddedSet = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_save_added_set)

        filledEDPExerciseType?.setAdapter(exerciseTypeAdapter)

        fabSaveAddedSet?.setOnClickListener {
            val set = getSetDetails()
            setViewModel.insertSet(set)
            val action =
                AddSetFragmentDirections
                    .actionAddSetFragmentToEditRoutineFragment(args.routine)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun getSetDetails() : Set {

        val editTextSetPerformOrder = view?.findViewById<EditText>(R.id.edittext_set_performOrder)
        val editTextSetExercise = view?.findViewById<EditText>(R.id.edittext_set_exercise)
        val editTextSetGoal = view?.findViewById<EditText>(R.id.edittext_set_goal)

        val performOrder = editTextSetPerformOrder?.text.toString().toInt()
        val exercise = editTextSetExercise?.text.toString()
        val exerciseType = ExerciseType.REP_BASED
        val goal = editTextSetGoal?.text.toString().toInt()
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