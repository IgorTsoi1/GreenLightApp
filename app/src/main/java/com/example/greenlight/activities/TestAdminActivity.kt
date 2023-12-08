package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityTestAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Test
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ServiceBuilder.buildService(Api::class.java)
        binding = ActivityTestAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this@TestAdminActivity, AdminActivity::class.java)
            startActivity(intent)
        }
        binding.buttonAdd.setOnClickListener {
            val testName = binding.testNameEditText.text.toString().trim()
            if(testName.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("name",testName)
                createTest(data)
            }
        }



    }

    private fun createTest(data: JsonObject) {
        api.createTest(data).enqueue(object:Callback<Test>{
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
             if(response.isSuccessful){
                 Toast.makeText(applicationContext, "successful", Toast.LENGTH_SHORT).show()
                 val intent = Intent(this@TestAdminActivity, AdminActivity::class.java)
                 startActivity(intent)
             }
            }
            override fun onFailure(call: Call<Test>, t: Throwable) {
                Log.e("zxc2134",t.message.toString())
            }

        })
    }
}