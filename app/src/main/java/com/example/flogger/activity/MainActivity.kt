package com.example.flogger.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.flogger.R
import com.example.flogger.fragment.RoutineListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Begin the fragment transaction
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.frame_fragment_container, RoutineListFragment())
        // Complete the changes added above
        ft.commit()
    }

   /* private val routinesViewModel: RoutinesViewModel = TODO()
    private lateinit var routineListAdapter: RoutineListAdapter
*//*    private lateinit var routines: List<Routine>*//*
    private val newRoutineActivityRequestCode = 1
    private val editRoutineActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    private fun initData()
    {
        *//*routinesViewModel = ViewModelProvider(this).get(RoutinesViewModel::class.java)

        routinesViewModel.allRoutines.observe(this, Observer { routines: List<Routine> ->
            this.routines = routines
            routineListAdapter.setRoutines(routines)
        })*//*
    }

    private fun initView() {

        *//*val addRoutineFab = findViewById<FloatingActionButton>(R.id.routine_add_fab)

        addRoutineFab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewRoutineActivity::class.java)
            startActivityForResult(intent, newRoutineActivityRequestCode)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.routine_recyclerview)
        routineListAdapter = RoutineListAdapter(this)
        recyclerView.adapter = routineListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        routineListAdapter.setEditOnClickListener {
            val intent = Intent(this@MainActivity, EditRoutineActivity::class.java)
            intent.putExtra("routineId", it.routineId)
            startActivityForResult(intent, editRoutineActivityRequestCode)
        }

        routineListAdapter.setDeleteOnClickListener {
            *//**//*routineViewModel.delete(it)*//**//*
        }*//*
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       *//* if (requestCode == newRoutineActivityRequestCode && resultCode == Activity.RESULT_OK) {
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
            }*//*
        }*/
    }
