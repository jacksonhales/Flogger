package com.example.flogger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.entity.Set
import kotlinx.android.synthetic.main.set_recyclerview_item.view.*

class SetListAdapter (var sets: ArrayList<Set>) : RecyclerView.Adapter<SetListAdapter.SetViewHolder>(){

    private var editListener: ((set: Set) -> Unit)? = null
    private var deleteListener: ((set: Set) -> Unit)? = null

    fun setEditOnClickListener(listener: (set: Set) -> Unit) {
        this.editListener = listener
    }

    fun setDeleteOnClickListener(listener: (set: Set) -> Unit) {
        this.deleteListener = listener
    }

    fun refreshAdapter(newSets : List<Set>){
        sets.clear()
        sets.addAll(newSets)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromRoutine = sets[from]
        sets.removeAt(from)
        if (to < from) {
            sets.add(to, fromRoutine)
        } else {
            sets.add(to - 1, fromRoutine)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SetViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.set_recyclerview_item, parent, false))

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = sets[position]
        holder.bind(current)
    }

    override fun getItemCount() = sets.size

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val setExercise: TextView = itemView.findViewById(R.id.textview_set_exercise)

        init {
            itemView.button_edit_set.setOnClickListener { editListener?.invoke(sets[adapterPosition])}
            itemView.button_delete_set.setOnClickListener { deleteListener?.invoke(sets[adapterPosition])}
        }

        fun bind(item: Set)
        {
            setExercise.text = item.exercise
        }
    }
}