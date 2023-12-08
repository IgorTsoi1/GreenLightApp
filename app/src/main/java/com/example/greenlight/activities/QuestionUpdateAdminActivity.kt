package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.databinding.ActivityQuestionUpdateAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Question
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionUpdateAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionUpdateAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ServiceBuilder.buildService(Api::class.java)
        binding = ActivityQuestionUpdateAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val questionId = intent.getStringExtra("QUESTION_ID")
        
        binding.backButton.setOnClickListener { 
            val intent = Intent(this@QuestionUpdateAdminActivity, AdminActivity::class.java)
            startActivity(intent)
        }
        
        binding.buttonAdd.setOnClickListener { 
            val question = binding.questionEditText.text.toString().trim()
            if(question.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("question",question)
                editQuestion(questionId.toString(),data)
            }
        }
    }

    private fun editQuestion(questionId: String, data: JsonObject) {
        api.updateQuestion(questionId,data).enqueue(object:Callback<ApiResponse<Question>>{
            override fun onResponse(
                call: Call<ApiResponse<Question>>,
                response: Response<ApiResponse<Question>>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "updated", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@QuestionUpdateAdminActivity,AdminActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ApiResponse<Question>>, t: Throwable) {
                Log.e("zxc12345",t.message.toString())
            }

        })
    }
}