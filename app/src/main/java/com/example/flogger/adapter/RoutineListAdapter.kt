package com.example.flogger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.relationships.RoutineWithSets
import kotlinx.android.synthetic.main.routine_recyclerview_item.view.*

class RoutineListAdapter internal constructor(context: Context) : RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var routines = emptyList<RoutineWithSets>() // cached copy of routines
    private var editListener: ((routine: RoutineWithSets) -> Unit)? = null
    private var deleteListener: ((routine: RoutineWithSets) -> Unit)? = null

    fun setEditOnClickListener(listener: (routine: RoutineWithSets) -> Unit) {
        this.editListener = listener
    }

    fun setDeleteOnClickListener(listener: (routine: RoutineWithSets) -> Unit) {
        this.deleteListener = listener
    }

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routineNameView: TextView = itemView.findViewById(R.id.textview_routine_name)

        init {
            itemView.button_edit_routine.setOnClickListener { editListener?.invoke(routines[adapterPosition])}
            itemView.button_delete_routine.setOnClickListener { deleteListener?.invoke(routines[adapterPosition])}
        }

        fun bind(item: RoutineWithSets)
        {
            routineNameView.text = item.routine.name
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

    internal fun setRoutines(routines: List<RoutineWithSets>) {
        this.routines = routines
        notifyDataSetChanged()
    }
}