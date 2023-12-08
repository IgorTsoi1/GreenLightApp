package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityTestUpdateAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Test
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestUpdateAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestUpdateAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestUpdateAdminBinding.inflate(layoutInflater)
        api = ServiceBuilder.buildService(Api::class.java)
        setContentView(binding.root)

        val testId = intent.getStringExtra("TEST_ID")

        binding.backButton.setOnClickListener {
            val intent = Intent(this@TestUpdateAdminActivity,AdminActivity::class.java)
            startActivity(intent)
        }
        binding.editButton.setOnClickListener {
            val testName = binding.testNameEditText.text.toString().trim()
            if(testName.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("name",testName)
                updateTest(testId.toString(),data)
            }
        }
    }
    private fun updateTest(testId: String, data: JsonObject) {
        api.updateTest(testId,data).enqueue(object:Callback<Test>{
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "Updated", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@TestUpdateAdminActivity,AdminActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
             Log.e("zxc21345",t.message.toString())
            }

        })
    }
}