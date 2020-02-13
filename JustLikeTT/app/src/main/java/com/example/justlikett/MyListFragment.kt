package com.example.justlikett


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my_list.*

/**
 * A simple [Fragment] subclass.
 */
class MyListFragment(
    var c: Context
) : Fragment(
) {

    lateinit var adapter:MyListRecyclerViewAdapter
    lateinit var pref: SharedPreferences
    lateinit var myData:MyData
    lateinit var edit: SharedPreferences.Editor
    lateinit var scoreList:ArrayList<Double>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun computeScore():Double{

        // check
        for(i in scoreList){
            if(i == -2.0) {
                Toast.makeText(c, "성적을 입력하지 않은 과목이 있습니다.", Toast.LENGTH_SHORT).show()
                return 0.0
            }
        }
        var score = 0.0
        var credit = 0.0
        scoreList = adapter.getScore()
        for((index, i) in myData.fixList.withIndex()){
            if(scoreList[index] >= 0){
                score += scoreList[index] * (i.credit.toDouble())
                credit += i.credit.toDouble()
            }
        }
        return score / credit
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init(){

        scoreList = ArrayList()

        pref = context!!.getSharedPreferences("myData", Activity.MODE_PRIVATE)
        edit = pref.edit()
        var gson = Gson()
        var json = pref.getString("myData", "")
        Log.d("json", json)

        if(json != ""){
            myData = gson.fromJson(json, MyData::class.java)
        } else {
            var fixList = ArrayList<LectureItem>()
            var tempList = ArrayList<LectureItem>()
            myData = MyData(fixList, tempList)
        }
        Log.d("myData", myData.toString())

        var credit = 0
        for(i in myData.fixList){
            credit += i.credit.toInt()
            scoreList.add(-2.0)
        }
        credit_count.text = credit.toString() + "학점"
        lect_count.text = myData.fixList.size.toString() + "과목"

        var itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)


        adapter = MyListRecyclerViewAdapter(c, myData.fixList, scoreList)
        var layoutManager = LinearLayoutManager(c, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
//        recyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        recyclerView.adapter = adapter

        addLect.setOnClickListener {
            var intent = Intent(c, SearchActivity::class.java)
            startActivity(intent)
        }

        computeScore.setOnClickListener {
            var score = computeScore()
            my_score.text = "평점: %.2f".format(score)
        }

    }
}
