package com.example.flogger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flogger.R
import com.example.flogger.entity.Set
import com.example.flogger.relationships.RoutineWithSets
import kotlinx.android.synthetic.main.routine_recyclerview_item.view.*

class SetListAdapter internal constructor(context: Context) : RecyclerView.Adapter<SetListAdapter.SetViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sets = emptyList<Set>() // cached copy of routines

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val setPerformOrderView: TextView = itemView.findViewById(R.id.textview_set_performOrder)

        fun bind(item: Set)
        {
            setPerformOrderView.text = item.performOrder.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val itemView = inflater.inflate(R.layout.set_recyclerview_item, parent, false)
        return SetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val current = sets[position]
        holder.bind(current)
    }

    override fun getItemCount() = sets.size

    internal fun setSets(sets: List<Set>) {
        this.sets = sets
        notifyDataSetChanged()
    }
}