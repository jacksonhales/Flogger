package com.example.flogger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.entity.Set

class SetListViewOnlyAdapter (var sets: ArrayList<Set>) : RecyclerView.Adapter<SetListViewOnlyAdapter.SetViewHolder>(){

   fun refreshAdapter(newSets : List<Set>){
        sets.clear()
        sets.addAll(newSets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SetViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.set_view_only_recyclerview_item, parent, false))

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = sets[position]
        holder.bind(current)
    }

    override fun getItemCount() = sets.size

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val setExercise: TextView = itemView.findViewById(R.id.textview_set_exercise)

        init {
        }

        fun bind(item: Set)
        {
            setExercise.text = item.exercise
        }
    }
}