package com.example.flogger.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flogger.R
import com.example.flogger.activity.MainActivity
import com.example.flogger.entity.Set
import com.example.flogger.enumeration.ExerciseType
import com.example.flogger.viewmodel.SetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_set.*
import kotlinx.android.synthetic.main.fragment_edit_set.edittext_set_exercise
import kotlinx.android.synthetic.main.fragment_edit_set.edittext_set_goal
import kotlinx.android.synthetic.main.fragment_edit_set.edittext_set_performOrder
import kotlinx.android.synthetic.main.fragment_edit_set.filled_edp_exerciseType

@AndroidEntryPoint
class EditSetFragment : Fragment() {

    private val args: EditSetFragmentArgs by navArgs()
    private var passedSet: Set? = null
    private var updatedSet: Set? = null
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

        filled_edp_exerciseType.setAdapter(exerciseTypeAdapter)

        passedSet = args.set

        edittext_set_performOrder.setText(passedSet?.performOrder.toString())
        edittext_set_exercise.setText(passedSet?.exercise)
        filled_edp_exerciseType.setText(passedSet?.exerciseType?.fName)
        edittext_set_goal.setText(passedSet?.goal.toString())

        fab_update_set.setOnClickListener {
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
        val performOrder = edittext_set_performOrder.text.toString().toInt()
        val exercise = edittext_set_exercise.text.toString()
        val exerciseType = ExerciseType.REP_BASED
        val goal = edittext_set_goal.text.toString().toInt()

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