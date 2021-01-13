package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
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
class EditSetFragment : Fragment() {

    private val args: EditSetFragmentArgs by navArgs()
    private var passedSet: Set? = null
    private var updatedSet: Set? = null
    private val setViewModel: SetViewModel by viewModels()
    private var filledEDPExerciseType: AutoCompleteTextView? = null
    private var editTextSetPerformOrder: EditText? = null
    private var editTextSetExercise: EditText? = null
    private var editTextSetGoal: EditText? = null
    private var fabUpdateSet: ExtendedFloatingActionButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {
        super.onResume()
        // Set title bar
        (activity as MainActivity?)
            ?.setActionBarTitle("Edit Set")
        (activity as MainActivity?)
            ?.setBackNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_set, container, false)
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

        filledEDPExerciseType = view?.findViewById<AutoCompleteTextView>(R.id.filled_edp_exerciseType)
        editTextSetPerformOrder = view?.findViewById<EditText>(R.id.edittext_set_performOrder)
        editTextSetExercise = view?.findViewById<EditText>(R.id.edittext_set_exercise)
        editTextSetGoal = view?.findViewById<EditText>(R.id.edittext_set_goal)
        fabUpdateSet = view?.findViewById<ExtendedFloatingActionButton>(R.id.fab_update_set)

        filledEDPExerciseType?.setAdapter(exerciseTypeAdapter)

        passedSet = args.set

        editTextSetPerformOrder?.setText(passedSet?.performOrder.toString())
        editTextSetExercise?.setText(passedSet?.exercise)
        filledEDPExerciseType?.setText(passedSet?.exerciseType?.fName)
        editTextSetGoal?.setText(passedSet?.goal.toString())

        fabUpdateSet?.setOnClickListener {
            updatedSet = getSetDetails()
            updatedSet?.let { it1 -> setViewModel.updateSet(it1) }

            val action =
                EditSetFragmentDirections
                    .actionEditSetFragmentToEditRoutineFragment(args.routine)
            view?.findNavController()?.navigate(action)
        }

    }

    private fun getSetDetails() : Set? {
        val setId = passedSet?.setId
        val performOrder = editTextSetPerformOrder?.text.toString().toInt()
        val exercise = editTextSetExercise?.text.toString()
        val exerciseType = ExerciseType.REP_BASED
        val goal = editTextSetGoal?.text.toString().toInt()

        return setId?.let {
            passedSet?.ownerId?.let { it1 ->
                Set(
                    it,
                    performOrder,
                    exercise,
                    exerciseType,
                    goal,
                    it1
                )
            }
        }
    }


}