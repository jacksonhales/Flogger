package com.example.flogger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.entity.Routine

class RoutineListAdapter (var routines: ArrayList<Routine>) : RecyclerView.Adapter<RoutineListAdapter.RoutineViewHolder>(){

    private var editListener: ((routine: Routine) -> Unit)? = null
    private var deleteListener: ((routine: Routine) -> Unit)? = null
    private var nameListener: ((routine: Routine) -> Unit)? = null

    fun setEditOnClickListener(listener: (routine: Routine) -> Unit) {
        this.editListener = listener
    }

    fun setDeleteOnClickListener(listener: (routine: Routine) -> Unit) {
        this.deleteListener = listener
    }

    fun setNameOnClickListener(listener: (routine: Routine) -> Unit) {
        this.nameListener = listener
    }

    fun refreshAdapter(newRoutines : List<Routine>){
        routines.clear()
        routines.addAll(newRoutines)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromRoutine = routines[from]
        routines.removeAt(from)
        if (to < from) {
            routines.add(to, fromRoutine)
        } else {
            routines.add(to - 1, fromRoutine)
        }
    }

    fun getItemByPosition(position: Int) : Routine {
        return routines[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RoutineViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.routine_recyclerview_item, parent, false))

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val current = routines[position]
        holder.bind(current)
    }

    override fun getItemCount() = routines.size

    inner class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val editRoutineButton: Button = itemView.findViewById(R.id.button_edit_routine)
        private val deleteRoutineButton: Button = itemView.findViewById(R.id.button_delete_routine)
        private val routineNameTextView: TextView = itemView.findViewById(R.id.textview_routine_name)

        init {
            editRoutineButton.setOnClickListener { editListener?.invoke(routines[adapterPosition])}
            deleteRoutineButton.setOnClickListener { deleteListener?.invoke(routines[adapterPosition])}
            routineNameTextView.setOnClickListener { nameListener?.invoke(routines[adapterPosition])}
        }

        fun bind(item: Routine)
        {
            routineNameTextView.text = item.name
        }
    }
}