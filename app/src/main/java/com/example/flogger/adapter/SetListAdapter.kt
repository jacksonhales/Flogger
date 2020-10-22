package com.example.flogger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.entity.Set
import kotlinx.android.synthetic.main.set_recyclerview_item.view.*
import org.w3c.dom.Text

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

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val setPerformOrderView: TextView = itemView.findViewById(R.id.textview_set_performOrder)
        private val setExercise: TextView = itemView.findViewById(R.id.textview_set_exercise)
        private val setExerciseType: TextView = itemView.findViewById(R.id.textview_set_exerciseType)
        private val setExerciseGoal: TextView = itemView.findViewById(R.id.textview_set_goal)

        init {
            itemView.button_edit_set.setOnClickListener { editListener?.invoke(sets[adapterPosition])}
            itemView.button_delete_set.setOnClickListener { deleteListener?.invoke(sets[adapterPosition])}
        }

        fun bind(item: Set)
        {
            setPerformOrderView.text = item.performOrder.toString()
            setExercise.text = item.exercise
            setExerciseType.text = item.exerciseType.toString()
            setExerciseGoal.text = item.goal.toString()
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
}