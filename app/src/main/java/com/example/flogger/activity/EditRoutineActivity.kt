package com.example.flogger.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.adapter.SetListAdapter
import com.example.flogger.entity.Routine
import com.example.flogger.entity.Set
import com.example.flogger.viewmodel.RoutineViewModel

class EditRoutineActivity : AppCompatActivity() {

    private lateinit var setListAdapter: SetListAdapter
    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var routine: Routine
    private lateinit var sets: List<Set>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        var editRoutineNameView = findViewById<EditText>(R.id.edit_routine_name)

        initData()
        initView()
    }

    private fun initData() {
        /*val routineId = intent.getLongExtra("routineId", 0)*/
        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

       /* routineViewModel.allRoutines.observe(this, Observer { routines: List<Routine> ->
            allRoutinesWithSets = routines
            routines.find {it.routine.routineId == routineId}?.let { routine = it }
            setListAdapter.setSets(routine.sets)
            editRoutineNameView.setText(routine.routine.name)
        })*/
    }

    private fun initView() {

        val buttonSaveRoutine = findViewById<Button>(R.id.button_save_routine)

        buttonSaveRoutine.setOnClickListener{

        }

        val setRecyclerView = findViewById<RecyclerView>(R.id.set_recyclerview)
        setListAdapter = SetListAdapter(this)
        setRecyclerView.adapter = setListAdapter
        setRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    companion object {
        const val EXTRA_REPLY = "com.example.flogger.editroutine.REPLY"
    }
}