package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.databinding.ActivityQuestionAdminBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Question
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionAdminBinding
    private lateinit var api: Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionAdminBinding.inflate(layoutInflater)
        api = ServiceBuilder.buildService(Api::class.java)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this@QuestionAdminActivity, AdminActivity::class.java)
            startActivity(intent)
        }

        val testId = intent.getStringExtra("TEST_ID")

        binding.buttonAdd.setOnClickListener {
            val question = binding.questionEditText.text.toString().trim()
            if(question.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("question",question)
                data.addProperty("test",testId)
                addQuestion(data)
            }
        }
    }

    private fun addQuestion(data:JsonObject) {
        api.createQuestion(data).enqueue(object:Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                if(response.isSuccessful){
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@QuestionAdminActivity,AdminActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<Question>, t: Throwable) {
               Log.d("zxc12345",t.message.toString())
            }

        })
    }
}