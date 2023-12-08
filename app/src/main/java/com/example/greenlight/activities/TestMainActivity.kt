package com.example.greenlight.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.adapters.RadioButtonAnswerAdapter
import com.example.greenlight.databinding.ActivityAdminBinding
import com.example.greenlight.databinding.ActivityTestMainBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Answer
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestMainBinding
    private lateinit var api: Api
    private var counter = 1;
    private var totalPoints = 0
    private  lateinit var questionsList : ArrayList<Question>
    private lateinit var answersList: ArrayList<Answer>
    private lateinit var radioButtonAnswerAdapter: RadioButtonAnswerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ServiceBuilder.buildService(Api::class.java)
        binding = ActivityTestMainBinding.inflate(layoutInflater)
        questionsList = ArrayList()
        answersList = ArrayList()
        radioButtonAnswerAdapter = RadioButtonAnswerAdapter()
        setContentView(binding.root)

        val testId = intent.getStringExtra("TEST_ID")

        onAnswerRadioButtonClick()

        getQuestionsByTestId(testId.toString())

        binding.nextButton.setOnClickListener {
            if(counter < questionsList.size) {
                counter++
                getQuestionsByTestId(testId.toString())
            } else {
                val intent = Intent(this@TestMainActivity,TestResultActivity::class.java)
                intent.putExtra("TOTAL_POINTS",totalPoints.toString())
                startActivity(intent)
            }
        }

        binding.prevButton.setOnClickListener {
            if(counter > 1) {
                counter--
                getQuestionsByTestId(testId.toString())
            }
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this@TestMainActivity,MainActivity::class.java)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            adapter = radioButtonAnswerAdapter
        }


    }

    private fun onAnswerRadioButtonClick(): Int {
        radioButtonAnswerAdapter.onItemClick = { answer ->
            totalPoints += answer.points!!.toInt()
        }
        return totalPoints
    }

    private fun getQuestionsByTestId(testId: String) {
       api.findQuestionByTest(testId).enqueue(object :Callback<ApiResponse<Question>>{
           override fun onResponse(
               call: Call<ApiResponse<Question>>,
               response: Response<ApiResponse<Question>>
           ) {
               if(response.isSuccessful){
                    questionsList = response.body()!!.result as ArrayList<Question>
                    binding.questionText.text = questionsList[counter-1].question.toString()
                    binding.counter.text = "$counter/${questionsList.size}"
                   getAllAnswersByQuestion(questionsList[counter-1].questionId.toString())
               }
           }

           private fun getAllAnswersByQuestion(questionId: String) {
               api.getAnswersByQuestionId(questionId).enqueue(object:Callback<ApiResponse<Answer>>{
                   override fun onResponse(
                       call: Call<ApiResponse<Answer>>,
                       response: Response<ApiResponse<Answer>>
                   ) {
                       if (response.isSuccessful){

                           answersList = response.body()!!.result as ArrayList<Answer>
                           radioButtonAnswerAdapter.setAnswerList(answersList)
                           //Если вам кажется это сложным то вы правы -> -< -> -< -> -< -> -< ->

                       }
                   }
                   override fun onFailure(call: Call<ApiResponse<Answer>>, t: Throwable) {
                      Log.e("zxc12345",t.message.toString())
                   }

               })
           }

           override fun onFailure(call: Call<ApiResponse<Question>>, t: Throwable) {
              Log.e("zxc12345",t.message.toString())
           }

       })
    }
}