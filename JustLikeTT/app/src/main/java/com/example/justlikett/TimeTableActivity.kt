package com.example.justlikett

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_time_table.*

class TimeTableActivity : AppCompatActivity() {

    var columns = 6
    lateinit var dataList:ArrayList<TableItem>

    lateinit var adapter:TableAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        dataInit()
        init()
    }

    fun dataInit(){
        dataList = ArrayList()
        for(j in 0 until 22){
            var time = -1
            if(j % 2 == 0){
                if((9 + (j/2)) > 12){
                    time = (9 + j/2) % 12
                } else {
                    time = 9 + (j / 2)
                }
                dataList.add(TableItem(0, "", "","", time))
                Log.v("Time", time.toString())
            } else {
                dataList.add(TableItem(0, "", "","", -1))
            }

            for(i in 1 until columns){
                dataList.add(TableItem(2, "", "","", -1))
            }
        }
    }
    fun init(){
        var layoutManager = GridLayoutManager(this, columns)
        adapter = TableAdapter(this, dataList)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

    }

}
