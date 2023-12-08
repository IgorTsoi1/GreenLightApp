package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityAnswerUpdateAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Answer
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerUpdateAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerUpdateAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerUpdateAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api = ServiceBuilder.buildService(Api::class.java)

        val answerId = intent.getStringExtra("ANSWER_ID")

        binding.backButton.setOnClickListener {
            val intent = Intent(this@AnswerUpdateAdminActivity, AdminActivity::class.java)
            startActivity(intent)
        }

        binding.updateButton.setOnClickListener {

            val answerText = binding.answerNameEditText.text.toString().trim()
            val points = binding.pointsEditText.text.toString().trim()

            if(answerText.isNotEmpty() && points.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("answer",answerText)
                data.addProperty("points",points)
             updateAnswer(answerId.toString(),data)
            }
        }
    }

    private fun updateAnswer(id: String, data: JsonObject) {
        api.updateAnswer(id,data).enqueue(object:Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
               if(response.isSuccessful){
                   Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                   val intent = Intent(this@AnswerUpdateAdminActivity,AdminActivity::class.java)
                   startActivity(intent)
               }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
               Log.e("zxc12345",t.message.toString())
            }

        })
    }
}