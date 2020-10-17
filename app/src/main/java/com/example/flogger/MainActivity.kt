package com.example.flogger

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var routineViewModel: RoutineViewModel
    private val newRoutineActivityRequestCode = 1
    private val editRoutineActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.routine_recyclerview)
        val addRoutineFab = findViewById<FloatingActionButton>(R.id.routine_add_fab)
        val adapter = RoutineListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        routineViewModel.allRoutines.observe(this, Observer { routines ->
                                                                    // update cached copy of routines in the adapter
                                                                    routines?.let{adapter.setRoutines(it)}
        })

        addRoutineFab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewRoutineActivity::class.java)
            startActivityForResult(intent, newRoutineActivityRequestCode)
        }

        adapter.setOnItemClickListener {
            val intent = Intent(this@MainActivity, EditRoutineActivity::class.java)
            intent.putExtra("routine", it)
            startActivityForResult(intent, editRoutineActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newRoutineActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewRoutineActivity.EXTRA_REPLY)?.let {
                val routine = Routine(0, it)
                routineViewModel.insert(routine)
            }
        }
        else if (requestCode == editRoutineActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Routine>(EditRoutineActivity.EXTRA_REPLY)?.let {
                routineViewModel.update(it)
            }
        }
        else {
                Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
