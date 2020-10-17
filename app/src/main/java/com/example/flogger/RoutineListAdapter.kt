package com.example.flogger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.routine_recyclerview_item.view.*

class RoutineListAdapter internal constructor(context: Context) : RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var routines = emptyList<Routine>() // cached copy of routines
    private var listener: ((routine: Routine) -> Unit)? = null

    fun setOnItemClickListener(listener: (routine: Routine) -> Unit) {
        this.listener = listener
    }

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routineNameView: TextView = itemView.findViewById(R.id.textview_routine_name)

        init {
            itemView.button_edit_routine.setOnClickListener { listener?.invoke(routines[adapterPosition])}
        }

        fun bind(item: Routine)
        {
            routineNameView.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val itemView = inflater.inflate(R.layout.routine_recyclerview_item, parent, false)
        return RoutineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val current = routines[position]
        holder.bind(current)
    }

    override fun getItemCount() = routines.size

    internal fun setRoutines(routines: List<Routine>) {
        this.routines = routines
        notifyDataSetChanged()
    }
}