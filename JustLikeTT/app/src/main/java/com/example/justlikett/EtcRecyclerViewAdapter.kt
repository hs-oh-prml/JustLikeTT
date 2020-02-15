package com.example.justlikett

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class EtcRecyclerViewAdapter(
    val context: Context,
    var items:ArrayList<LectureItem>) : RecyclerView.Adapter<EtcRecyclerViewAdapter.ViewHolder>(){


    var lastSelectedPosition = -1



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EtcRecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_etc, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null)
            return 0
        return items.size
    }

    override fun onBindViewHolder(holder: EtcRecyclerViewAdapter.ViewHolder, position: Int) {
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

        holder.star_off.setOnClickListener {
            it.visibility = View.GONE
            holder.star_on.visibility = View.VISIBLE
        }

        holder.star_on.setOnClickListener {
            it.visibility = View.GONE
            holder.star_off.visibility = View.VISIBLE
        }

        holder.delBtn.setOnClickListener {
            //TODO
        }

        if(position == lastSelectedPosition){
            holder.radio.isChecked = true
            holder.frame.setBackgroundResource(R.color.selected_item)
            holder.delBtn.visibility = View.VISIBLE

//            Log.d("last", lastData.toString())
//            if(lastData.lectNum != data.lectNum && lastData.lectNum != "-1"){
//                listener.choiceCancel(lastData)
//            }
        } else {
            holder.radio.isChecked = false
            holder.frame.setBackgroundResource(R.color.white)
            holder.delBtn.visibility = View.GONE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var frame: LinearLayout
        var delBtn: TextView
        var radio: RadioButton

        var star_on : ImageView
        var star_off : ImageView

        var lectName: TextView
        var professor: TextView
        var timeNroom: TextView
        var grade: TextView
        var pobtDiv: TextView
        var credit: TextView
        var lectNum: TextView


        init {
            frame = itemView.findViewById(R.id.etc_frame)
            delBtn = itemView.findViewById(R.id.etc_del_btn)
            radio = itemView.findViewById(R.id.etc_radioBtn)
            itemView.setOnClickListener {
                lastSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }

            star_on = itemView.findViewById(R.id.star_on)
            star_off = itemView.findViewById(R.id.star_off)


            lectName = itemView.findViewById(R.id.etc_name)
            professor = itemView.findViewById(R.id.etc_professor)
            timeNroom = itemView.findViewById(R.id.etc_timeNroom)
            grade = itemView.findViewById(R.id.etc_grade)
            pobtDiv = itemView.findViewById(R.id.etc_pobtDiv)
            credit = itemView.findViewById(R.id.etc_credit)
            lectNum = itemView.findViewById(R.id.etc_lectNum)

        }
    }


}
