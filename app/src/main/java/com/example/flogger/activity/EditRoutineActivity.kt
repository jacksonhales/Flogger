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
import com.example.flogger.adapter.RoutineListAdapter
import com.example.flogger.adapter.SetListAdapter
import com.example.flogger.relationships.RoutineWithSets
import com.example.flogger.viewmodel.RoutineViewModel

class EditRoutineActivity : AppCompatActivity() {

    private lateinit var editRoutineNameView: EditText
    private lateinit var setListAdapter: SetListAdapter
    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var routine: RoutineWithSets
    private lateinit var allRoutinesWithSets: List<RoutineWithSets>

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        setListAdapter = SetListAdapter(this)
        val setRecyclerView = findViewById<RecyclerView>(R.id.set_recyclerview)

        setRecyclerView.adapter = setListAdapter
        setRecyclerView.layoutManager = LinearLayoutManager(this)


        initView()
        initData()
    }

    private fun initData() {
        val routineId = intent.getLongExtra("routineId", 0)
        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        routineViewModel.allRoutinesWithSets.observe(this, Observer { routines: List<RoutineWithSets> ->
            allRoutinesWithSets = routines
            routines.find {it.routine.routineId == routineId}?.sets?.let { setListAdapter.setSets(it) }
        })
    }

    private fun initView() {
        editRoutineNameView = findViewById(R.id.edit_routine_name)

        val buttonSaveRoutine = findViewById<Button>(R.id.button_save_routine)

        buttonSaveRoutine.setOnClickListener{

        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.flogger.editroutine.REPLY"
    }
}