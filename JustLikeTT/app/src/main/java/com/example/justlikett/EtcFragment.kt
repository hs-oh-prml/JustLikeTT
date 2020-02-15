package com.example.justlikett


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_etc.*
import kotlinx.android.synthetic.main.fragment_my_list.*

/**
 * A simple [Fragment] subclass.
 */
class EtcFragment(var c: Context) : Fragment() {


    var dbHelper = DBHelper(c)
    lateinit var adapter:EtcRecyclerViewAdapter
    lateinit var dataList:ArrayList<LectureItem>
    lateinit var pref: SharedPreferences
    lateinit var layoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_etc, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    fun init(){
        dataList = ArrayList()
        dbHelper.importDB()

        etc_search.setOnClickListener {
            var temp_Name = name_input.text.toString()
            var temp_professor = professor_input.text.toString()
            if( temp_Name == ""){
                //오류
                Toast.makeText(c, "과목명을 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                dataList = dbHelper.search2(temp_Name,temp_professor)
                if(dataList.isEmpty()){
                    Toast.makeText(c, "해당하는 과목이 없습니다.",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(c, dataList[0].professor, Toast.LENGTH_SHORT).show()
                }
            }

            adapter = EtcRecyclerViewAdapter(c, dataList)
            layoutManager = LinearLayoutManager(c, RecyclerView.VERTICAL, false)
            etc_recyclerView.layoutManager = layoutManager
            etc_recyclerView.adapter = adapter

        }

        adapter = EtcRecyclerViewAdapter(c, dataList)
        layoutManager = LinearLayoutManager(c, RecyclerView.VERTICAL, false)
        etc_recyclerView.layoutManager = layoutManager
        etc_recyclerView.adapter = adapter
    }
}
