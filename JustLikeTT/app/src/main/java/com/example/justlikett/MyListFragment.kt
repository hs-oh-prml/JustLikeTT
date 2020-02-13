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

    fun init(){

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
        }
        credit_count.text = credit.toString() + "학점"
        lect_count.text = myData.fixList.size.toString() + "과목"



        adapter = MyListRecyclerViewAdapter(c, myData.fixList)
        var layoutManager = LinearLayoutManager(c, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
//        recyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        recyclerView.adapter = adapter

        addLect.setOnClickListener {
            var intent = Intent(c, SearchActivity::class.java)
            startActivity(intent)
        }

    }
}
