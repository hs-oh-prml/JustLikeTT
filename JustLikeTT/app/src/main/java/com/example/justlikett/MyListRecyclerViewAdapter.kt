package com.example.justlikett

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyListRecyclerViewAdapter (
    val context: Context,
    var items:ArrayList<LectureItem>
): RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_lecture, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null)
            return 0
        return items.size
    }

    var lastSelectedPosition = -1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items[position]
        holder.lectName.text = data.lectName
        holder.lectNum.text = data.lectNum

        var professorList = data.professor.split(",")
        var professor = ""
        for(i in professorList){
            professor += i.replace(" ", "") + " "
        }
        holder.professor.text = professor


        holder.credit.text = data.credit + "학점"

        var timeNroomList = data.timeNroom.split(",")
        var timeNroom = ""
        for(i in timeNroomList){
            timeNroom += i.replace(" ", "") + " "
        }
        holder.timeNroom.text = timeNroom


        holder.grade.text = data.grade + "학년"
        holder.pobtDiv.text= data.pobtDiv

//
//        holder.itemView.setOnClickListener {
//            currentSelectedPosition = position
//            if(currentSelectedPosition != lastSelectedPosition){
//                holder.frame.setBackgroundResource(R.color.selected_item)
//                holder.addBtn.visibility = VISIBLE
//                notifyItemChanged(currentSelectedPosition)
//                lastSelectedPosition = currentSelectedPosition
//            } else {
//                holder.frame.setBackgroundResource(R.color.white)
//                holder.addBtn.visibility = GONE
//                lastSelectedPosition = -1
//                notifyItemChanged(currentSelectedPosition)
//            }
//
//        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var frame: LinearLayout
        var addBtn: TextView
        var radio: RadioButton

        var lectName:TextView
        var professor:TextView
        var timeNroom:TextView
        var grade:TextView
        var pobtDiv:TextView
        var credit:TextView
        var lectNum:TextView
        init {
            frame = itemView.findViewById(R.id.frame)
            addBtn = itemView.findViewById(R.id.add_btn)
            radio = itemView.findViewById(R.id.radioBtn)
            itemView.setOnClickListener {
                lastSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }

            lectName = itemView.findViewById(R.id.name)
            professor = itemView.findViewById(R.id.professor)
            timeNroom = itemView.findViewById(R.id.timeNroom)
            grade = itemView.findViewById(R.id.grade)
            pobtDiv = itemView.findViewById(R.id.pobtDiv)
            credit = itemView.findViewById(R.id.credit)
            lectNum = itemView.findViewById(R.id.lectNum)
        }
    }

}