package com.example.justlikett

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.timeTable
import kotlinx.android.synthetic.main.item_lecture.*
import kotlinx.android.synthetic.main.item_major.*
import kotlinx.android.synthetic.main.item_major.view.*
import org.w3c.dom.Text
import kotlin.math.absoluteValue
import kotlin.random.Random

class SearchActivity : AppCompatActivity() {

    var dbHelper = DBHelper(this)
    val REQUEST_MAJOR = 1111

    lateinit var majorCode: String
    var majorName = ""
    lateinit var weekList:List<String>
    lateinit var dataList:ArrayList<LectureItem>

    lateinit var adapter:LectRecyclerViewAdapter
    lateinit var listener:LectRecyclerViewAdapter.addListener

    lateinit var pref: SharedPreferences
    lateinit var myData:MyData
    lateinit var edit:SharedPreferences.Editor

    lateinit var timeTableInfo:ArrayList<ArrayList<LectureItem>>

    lateinit var color: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        color = this.resources.getStringArray(R.array.colorList)

        pref = this.getSharedPreferences("myData", Activity.MODE_PRIVATE)
        edit = pref.edit()
        var gson = Gson()
        var json = pref.getString("myData", "")
        Log.v("json_2", json)
        if(json != ""){
            myData = gson.fromJson(json, MyData::class.java)
        } else {
            var fixList = ArrayList<LectureItem>()
            var tempList = ArrayList<LectureItem>()
            myData = MyData(fixList, tempList)
        }

        weekList = listOf("월", "화", "수", "목", "금")

        initTable(weekList)
        init()
    }

    fun getChild(row:Int, col:Int): TextView {
        Log.d("index", row.toString() + ", " + col.toString())
        var index = (timeTable.columnCount * row) + col
        return timeTable.getChildAt(index) as TextView
    }


    fun refreshTable(){
        for(i in 1..22){
            for(j in 1..weekList.size){
//                var cell = row.getChildAt(j)
                var cell = getChild(i, j)

                cell.setBackgroundResource(R.color.white)
            }
        }
        for((index, i) in myData.fixList.withIndex()){
            addTable(i, 1, index)
        }
    }

    fun init(){
        timeTableInfo = ArrayList()
        for(i in 0..22){
            var infoRow = ArrayList<LectureItem>()
            for(j in 0..weekList.size){
                infoRow.add(LectureItem("","","","","","","","","",""))
            }
            timeTableInfo.add(infoRow)
        }


        refreshTable()

        dataList = ArrayList()
        dbHelper.importDB()

        dataList = dbHelper.getList(null)

        listener = object: LectRecyclerViewAdapter.addListener{
            override fun addLecture(lect: LectureItem) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                if(lect.timeNroom == ""){
                    return
                }

                var timeNroomList = lect.timeNroom.split(",")
                for(i in timeNroomList){
                    var timeList = parseTime(lect, lect.timeNroom)
                    if(isDuplicate(timeList)){
                        Toast.makeText(applicationContext, "겹치는 시간표가 존재합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        myData.fixList.add(lect)
                        var gson = Gson()
                        var json = gson.toJson(myData)
                        edit.putString("myData", json).commit()
                        refreshTable()
                    }
                }
            }

            override fun choiceLecture(lect: LectureItem) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                refreshTable()
                addTable(lect, 0, -1)
            }

            override fun choiceCancel(lect: LectureItem) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                addTable(lect, 2, -1)
            }
        }

        adapter = LectRecyclerViewAdapter(this, dataList, listener)

        var layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
//        recyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        recyclerView.adapter = adapter



        major.setOnClickListener {
            val intent = Intent(this, FindMajorActivity::class.java)
            startActivityForResult(intent, REQUEST_MAJOR)
        }

        searchBtn.setOnClickListener {
            var lectNum = search.text.toString()

            var checked = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            var mode = ""
            when(checked.text){
                "과목코드"-> mode = "lectNum"
                "과목명" -> mode = "lectName"
                "교수명" -> mode = "professor"
                else -> mode = "lectNum"
            }
            Log.v("Radio Button", mode)

            if(lectNum != ""){
                dataList = dbHelper.search(lectNum, mode)
//                Log.v("result", result.toString())
                major.text = dataList[0].major
                search.text.clear()
                adapter = LectRecyclerViewAdapter(this, dataList, listener)
                recyclerView.adapter = adapter
            }
        }
    }


    fun parseTime(lect:LectureItem, target:String):ArrayList<timeCell>{
        var result = ArrayList<timeCell>()

        var timeNroom = target.replace(" ", "")
        var week = timeNroom[0].toString()
        var startTime = timeNroom[1].toString() + timeNroom[2].toString()
        var endTime = timeNroom[4].toString() + timeNroom[5].toString()
        var roomData = timeNroom.substring(6, timeNroom.length).replace("(", "")
        roomData = roomData.replace(")", " ")
        var room = roomData.split(" ")[0]

        var col = -1
        when(week) {
            "월" -> {
                col = 1
            }
            "화" -> {
                col = 2
            }
            "수" -> {
                col = 3
            }
            "목" -> {
                col = 4
            }
            "금" -> {
                col = 5
            }
            "토" -> {
                col = 6
                var weekList = listOf("월", "화", "수", "목", "금")
                initTable(weekList)
            }
        }

        var startRow = startTime.toInt()
        var endRow = endTime.toInt()
        var time = endRow - startRow
        for(i in 0..time){
            result.add(timeCell(startRow + i, col, room, lect.lectName, lect.professor))
        }
        return result
    }

    fun isDuplicate(timeCellList:ArrayList<timeCell>):Boolean {
        for(i in timeCellList){
            if(timeTableInfo[i.row - 1][i.col - 1].lectName != ""){
                return true
            }
        }
        return false
    }

    fun addTable(lect:LectureItem, mode:Int, index:Int){

        // mode 0 : Choice, 1 : Select, 2 : Cancel

        // Parse Time&Room
        if(lect.timeNroom  == ""){
            Toast.makeText(this, "강의시간 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        var eLearningCheck = lect.timeNroom.split("(e-러닝)")
        Log.d("E_check", eLearningCheck.toString())
        if(eLearningCheck[0] == " "){
            Toast.makeText(this, "강의시간이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        var timeNroomList = lect.timeNroom.split(",")
        for(i in timeNroomList){
            var timeCellList = parseTime(lect, i)
            var flag = 0
            for(j in timeCellList){

//                var row = timeTable.getChildAt(j.row) as ViewGroup
                var cell = getChild(j.row, j.col)

                if(mode == 0){
                    if(timeTableInfo[j.row - 1][j.col - 1].lectName != ""){
                        cell.setBackgroundResource(R.color.duplicate)
                    } else {
                        cell.setBackgroundResource(R.color.selected_item)
                    }
                } else if(mode == 1) {
                    cell.setBackgroundColor(Color.parseColor(color[index]))
                    if(flag == 0){
                        val name = lect.lectName.split("(")[0]
                        val str = name + "\n" + j.room + "\n" + j.professor
                        (cell as TextView).textSize = 6f
                        (cell as TextView).text = str
                    }
                    timeTableInfo[j.row - 1][j.col - 1] = lect
                }
                else {
                    cell.setBackgroundResource(R.color.white)
                }
                flag++
            }

        }
    }


    fun initTable(weekList:List<String>){

        timeTable.columnCount = weekList.size + 1
        timeTable.rowCount = 23
        for(i in 0 until timeTable.rowCount){
            for(j in 0 until timeTable.columnCount){
                var param = GridLayout.LayoutParams()
                param.height = GridLayout.LayoutParams.WRAP_CONTENT
                param.width = GridLayout.LayoutParams.WRAP_CONTENT
                param.setMargins(1)
                param.setGravity(Gravity.CENTER)
                param.columnSpec = GridLayout.spec(j)
                param.rowSpec = GridLayout.spec(i)

                var textView = TextView(this)
                textView.gravity = Gravity.CENTER
                textView.setBackgroundResource(R.color.white)

                var colSpan = GridLayout.spec(j, GridLayout.FILL)
                var rowSpan = GridLayout.spec(i, GridLayout.FILL)
                param.columnSpec = colSpan
                param.rowSpec = rowSpan

                if(i == 0 && j != 0){
                    var colSpan = GridLayout.spec(j, GridLayout.FILL, 1f)
                    param.columnSpec = colSpan
                    textView.text = weekList[j - 1]
                }
                if(i != 0 && j == 0){
                    if(i % 2 == 1){
                        if((9 + i / 2) > 12){
                            textView.text = ((9 + i / 2) % 12).toString()
                        } else {
                            textView.text = (9 + i / 2).toString()
                        }
                    }
                }
                if(j != 0){
                    var colSpan = GridLayout.spec(j, GridLayout.FILL, 1f)

                    param.columnSpec = colSpan
                }
                if(i != 0){
                    var rowSpan = GridLayout.spec(i, GridLayout.FILL, 1f)
//                    var rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 2, 1f)
                    if(i % 2 == 1){
                        param.setMargins(1,1,1,0)
                    } else {
                        param.setMargins(1,0,1,1)
                    }
                    param.rowSpec = rowSpan
                }

                textView.layoutParams = param
                timeTable.addView(textView)
            }

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_MAJOR){
            if(resultCode == Activity.RESULT_OK){
                majorName = data!!.getStringExtra("major")
                majorCode = data.getStringExtra("major_code")
                major.text = majorName
                dataList = dbHelper.getList(majorCode)
                adapter = LectRecyclerViewAdapter(this, dataList, listener)
                recyclerView.adapter = adapter
            }
        }
    }
}