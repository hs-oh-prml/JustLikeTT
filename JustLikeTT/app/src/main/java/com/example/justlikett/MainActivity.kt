package com.example.justlikett

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.Gravity.*
import android.view.View.GONE
import android.widget.*
import androidx.core.view.marginEnd
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

//    lateinit var pref: SharedPreferences
//    lateinit var myData:MyData
//    lateinit var edit: SharedPreferences.Editor
//
//    lateinit var color: Array<String>
//    lateinit var weekList:List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        weekList = listOf("월", "화", "수", "목", "금")
//        color = this.resources.getStringArray(R.array.colorList)
//
//        pref = this.getSharedPreferences("myData", Activity.MODE_PRIVATE)
//        edit = pref.edit()
//        var gson = Gson()
//        var json = pref.getString("myData", "")
//        Log.d("json", json)
//
//        if(json != ""){
//            myData = gson.fromJson(json, MyData::class.java)
//        } else {
//            var fixList = ArrayList<LectureItem>()
//            var tempList = ArrayList<LectureItem>()
//            myData = MyData(fixList, tempList)
//        }
//        Log.d("myData", myData.toString())


//        initView(weekList)
//        init()

//        refreshTable()
        init()
    }

    fun init(){
        val pagerAdapter = MainPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                when(position) {
                    0 -> {
                        tab.setText("시간표")
                    }
                    1 -> {
                        tab.setText("내 수업")
                    }
                    else -> {
                        tab.setText("더보기")
                    }

                }
            }
        }).attach()
    }

    val NUM_PAGES = 3

    private inner class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            when(position){
                0->{
                    return TableFragment(applicationContext)
                }
                1->{
                    return MyListFragment(applicationContext)

                }
                else->{
                    return EtcFragment()
                }
            }

        }

    }
//
//    fun parseTime(lect:LectureItem, target:String):ArrayList<timeCell>{
//        var result = ArrayList<timeCell>()
//
//        var timeNroom = target.replace(" ", "")
//        var week = timeNroom[0].toString()
//        var startTime = timeNroom[1].toString() + timeNroom[2].toString()
//        var endTime = timeNroom[4].toString() + timeNroom[5].toString()
//        var roomData = timeNroom.substring(6, timeNroom.length).replace("(", "")
//        roomData = roomData.replace(")", " ")
//        var room = roomData.split(" ")[0]
//
//        var col = -1
//        when(week) {
//            "월" -> {
//                col = 1
//            }
//            "화" -> {
//                col = 2
//            }
//            "수" -> {
//                col = 3
//            }
//            "목" -> {
//                col = 4
//            }
//            "금" -> {
//                col = 5
//            }
//            "토" -> {
//                col = 6
//                var weekList = listOf("월", "화", "수", "목", "금")
//                initView(weekList)
//            }
//        }
//
//        var startRow = startTime.toInt()
//        var endRow = endTime.toInt()
//        var time = endRow - startRow
//        for(i in 0..time){
//            result.add(timeCell(startRow + i, col, room, lect.lectName, lect.professor))
//        }
//        return result
//    }
//
//
//    fun refreshTable(){
//        for(i in 1..22){
//            for(j in 1..weekList.size){
////                var cell = row.getChildAt(j)
//                var cell = getChild(i, j)
//
//                cell.setBackgroundResource(R.color.white)
//            }
//        }
//        for((index, i) in myData.fixList.withIndex()){
//            Log.d("myList", i.lectName)
//            addTable(i, index)
//        }
//    }
//    fun getChild(row:Int, col:Int): TextView {
//        Log.d("index", row.toString() + ", " + col.toString())
//        var index = (timeTable.columnCount * row) + col
//        return timeTable.getChildAt(index) as TextView
//    }
//
//    fun addTable(lect:LectureItem, index:Int){
//        // Parse Time&Room
//        var timeNroomList = lect.timeNroom.split(",")
//        for(i in timeNroomList){
//            var timeCellList = parseTime(lect, i)
//            var flag = 0
//            for(j in timeCellList){
//
////                var row = timeTable.getChildAt(j.row) as ViewGroup
//                var cell = getChild(j.row, j.col)
//
//                cell.setBackgroundColor(Color.parseColor(color[index]))
//                if(flag == 0){
//                    val name = lect.lectName.split("(")[0]
//
//                    var professor = ""
//                    if(j.professor[0] == ' '){
//                        professor = j.professor.substring(1,j.professor.length)
//                    }
//
//                    val str = name + "\n" + j.room + "\n" + professor
//                    cell.textSize = 10f
//                    cell.text = str
//                    cell.gravity = LEFT
//
//                    var param = GridLayout.LayoutParams()
//                    var rowSpan = GridLayout.spec(j.row, timeCellList.size, GridLayout.FILL)
//                    var colSpan = GridLayout.spec(j.col, 1, GridLayout.FILL)
//
//                    param.rowSpec = rowSpan
//                    param.columnSpec = colSpan
//                    cell.layoutParams = param
//                } else {
//                    cell.visibility = GONE
//                }
//                flag++
//            }
//
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        refreshTable()
//    }
//
//    fun initView(weekList: List<String>){
//
//
//        var rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f)
//        var colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f)
//        var gridParam = GridLayout.LayoutParams(rowSpan, colSpan)
//
//        timeTable.columnCount = weekList.size + 1
//        timeTable.rowCount = 23
//        for(i in 0 until timeTable.rowCount){
//            for(j in 0 until timeTable.columnCount){
//                var param = GridLayout.LayoutParams()
//                param.height = GridLayout.LayoutParams.WRAP_CONTENT
//                param.width = GridLayout.LayoutParams.WRAP_CONTENT
//                param.setMargins(1)
//                param.setGravity(CENTER)
//                param.columnSpec = GridLayout.spec(j)
//                param.rowSpec = GridLayout.spec(i)
//
//                var textView = TextView(this)
//
//
//                var disp = DisplayMetrics()
//                var dwidth = disp.widthPixels
//                var dheight = disp.heightPixels
//                textView.gravity = CENTER
//                textView.setBackgroundResource(R.color.white)
//
//                var colSpan = GridLayout.spec(j, GridLayout.FILL)
//                var rowSpan = GridLayout.spec(i, GridLayout.FILL)
//                param.columnSpec = colSpan
//                param.rowSpec = rowSpan
//
//                if(i == 0 && j != 0){
//                    var colSpan = GridLayout.spec(j, GridLayout.FILL, 1f)
//                    param.columnSpec = colSpan
//                    textView.textSize = 10f
//                    textView.text = weekList[j - 1]
//                }
//                if(i != 0 && j == 0){
//                    if(i % 2 == 1){
//                        textView.gravity = TOP or RIGHT
//                        textView.textSize = 10f
//                        if((9 + i / 2) > 12){
//                            textView.text = ((9 + i / 2) % 12).toString()
//                        } else {
//                            textView.text = (9 + i / 2).toString()
//                        }
//                    }
//                }
//                if(j != 0){
//                    var colSpan = GridLayout.spec(j, GridLayout.FILL, 1f)
//                    param.columnSpec = colSpan
//                }
//                if(i != 0){
//                    var rowSpan = GridLayout.spec(i, GridLayout.FILL, 1f)
////                    var rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2, 1f)
//                    if(i % 2 == 1){
//                        param.setMargins(1,1,1,0)
//                    } else {
//                        param.setMargins(1,0,1,1)
//                    }
//                    param.rowSpec = rowSpan
//                }
//                if(i != 0 && j != 0){
//                    textView.width = (dwidth * (1/5) * 0.7).toInt()
//                    textView.height = (dheight * (1/24) * 0.8).toInt()
//                }
//                textView.layoutParams = param
//
//                timeTable.addView(textView)
//            }
//        }
//    }
//    // TEST
//    fun init(){
//        timeTable.setOnClickListener {
//            var intent = Intent(this, SearchActivity::class.java)
//            startActivity(intent)
//        }
//    }
}
