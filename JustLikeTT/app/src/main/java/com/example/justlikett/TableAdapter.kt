package com.example.justlikett

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TableAdapter(
    val context: Context,
    var items:ArrayList<TableItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    companion object{
        val TIME_CELL = 0
        val DATA_CELL = 1
        val NULL_CELL = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TIME_CELL -> {
                val v = LayoutInflater.from(context)
                    .inflate(R.layout.cell_type_time, parent, false)
                return TimeViewHolder(v)
            }
            DATA_CELL -> {
                val v = LayoutInflater.from(context)
                    .inflate(R.layout.cell, parent, false)
                return ViewHolder(v)
            }
            else -> {
                val v = LayoutInflater.from(context)
                    .inflate(R.layout.cell, parent, false)
                return ViewHolder(v)
            }
        }
    }

    override fun getItemCount(): Int {
        if (items == null)
            return 0
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        val data = items[position]

        when(holder){
            is ViewHolder-> {
                when(type){
                    NULL_CELL -> {
                        holder.title.text = "null"
                        holder.professor.text = ""
                        holder.room.text = ""

                    }
                    DATA_CELL -> {
                        holder.professor.text = data.professor
                        holder.room.text = data.room
                        holder.title.text = data.name
                    }
                }
            }
            is TimeViewHolder-> {
                if(data.time > 0){
                    holder.time.text = data.time.toString()
                } else {
                    holder.time.text = ""
                }
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var room: TextView
        var professor: TextView
        init {
            title = itemView.findViewById(R.id.lectName)
            room = itemView.findViewById(R.id.room)
            professor = itemView.findViewById(R.id.professor)

        }
    }
    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView
        init {
            time = itemView.findViewById(R.id.time)
        }
    }

}