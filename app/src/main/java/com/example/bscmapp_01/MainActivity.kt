package com.example.bscmapp_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://script.google.com/macros/s/AKfycbyShjMUMGoSZDgCzk86eQbndBsRmhyb8_mJVofrIS9eKbdhFiu0WLQ7TAsFO_PWTyMvPQ/"
    private val TAG: String = "CHECK_RESPONSE"
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var titleList: ArrayList<String>
    lateinit var valueList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataClass>()
        titleList = arrayListOf()
        valueList = arrayListOf()

        val adapter = AdapterClass(dataList)
        recyclerView.adapter = adapter

        getAllItems()
    }

    private fun getAllItems() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<DataClass>> {
            override fun onResponse(
                call: Call<List<DataClass>>,
                response: Response<List<DataClass>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (i in it) {
                            val elementName = i.serial
                            val elementValue = i.smrValue
                            val dataClass = DataClass(id = 0, serial = elementName, smrValue = elementValue)
                            dataList.add(dataClass)
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<DataClass>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}