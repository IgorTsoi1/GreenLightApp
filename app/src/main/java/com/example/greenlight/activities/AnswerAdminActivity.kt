package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.databinding.ActivityAnswerAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Answer
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Question
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionId = intent.getStringExtra("QUESTION_ID")
        api = ServiceBuilder.buildService(Api::class.java)

        binding.backButton.setOnClickListener {
            val intent = Intent(this@AnswerAdminActivity, AdminActivity::class.java)
            startActivity(intent)
        }
        binding.addButton.setOnClickListener {
            val answerText = binding.answerNameEditText.text.toString().trim()
            val points = binding.pointsEditText.text.toString().trim()

            if(answerText.isNotEmpty() && points.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("answer",answerText)
                data.addProperty("points",points)
                data.addProperty("question",questionId)
                createAnswer(data)
            }
        }


    }

    private fun createAnswer(data: JsonObject) {
        api.createAnswers(data).enqueue(object:Callback<ApiResponse<Answer>>{
            override fun onResponse(
                call: Call<ApiResponse<Answer>>,
                response: Response<ApiResponse<Answer>>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AnswerAdminActivity,AdminActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<ApiResponse<Answer>>, t: Throwable) {
                Log.e("zxc12345",t.message.toString())
            }

        })
    }
}