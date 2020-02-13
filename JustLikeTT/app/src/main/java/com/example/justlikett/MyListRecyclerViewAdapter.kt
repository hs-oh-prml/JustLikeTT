package com.example.justlikett

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_search.view.*

class MyListRecyclerViewAdapter (
    val context: Context,
    var items:ArrayList<LectureItem>,
    var scoreList:ArrayList<Double>
): RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_my_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null)
            return 0
        return items.size
    }

    var lastSelectedPosition = -1


    var score = ""

    fun getScore():ArrayList<Double> = scoreList

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

        holder.scoreView.setOnCheckedChangeListener { radioGroup, i ->
            score = radioGroup.findViewById<RadioButton>(i).text.toString()
            when(score){
                "A+"->{
                    scoreList[position] = 4.5
                }
                "A"->{
                    scoreList[position] = 4.0
                }
                "B+"->{
                    scoreList[position] = 3.5
                }
                "B"->{
                    scoreList[position] = 3.0
                }
                "C+"->{
                    scoreList[position] = 2.5
                }
                "C"->{
                    scoreList[position] = 2.0
                }
                "D+"->{
                    scoreList[position] = 1.5
                }
                "D"->{
                    scoreList[position] = 1.0
                }
                "F"->{
                    scoreList[position] = 0.0
                }
                "P"->{
                    scoreList[position] = -1.0
                }
                "NP"->{
                    scoreList[position] = -1.0
                }
            }
            Log.v("score", score)
        }

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

        var scoreView: RadioGroup
        init {
            frame = itemView.findViewById(R.id.frame)
            addBtn = itemView.findViewById(R.id.delete_btn)
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

            scoreView = itemView.findViewById(R.id.score)
            var scoreArray = context.resources.getStringArray(R.array.score)

            for(i in scoreArray){
                var rb = RadioButton(context)
                rb.setTextColor(context.getColor(R.color.white))
                rb.text = i
                rb.gravity = CENTER
                rb.setButtonDrawable(android.R.color.transparent)
                rb.setBackgroundResource(R.drawable.selector)

                scoreView.addView(rb)
            }
        }
    }

}