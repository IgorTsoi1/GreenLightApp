package com.example.greenlight.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.adapters.AnswerAdminAdapter
import com.example.greenlight.adapters.ChoiceQuestionAdminAdapter
import com.example.greenlight.adapters.ChoiceTestAdminAdapter
import com.example.greenlight.adapters.QuestionAdminAdapter
import com.example.greenlight.adapters.TestAdminAdapter
import com.example.greenlight.databinding.ActivityAdminBinding
import com.example.greenlight.databinding.ChoiceQuestionDialogBinding
import com.example.greenlight.databinding.ChoiceTestDialogBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.Answer
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Question
import com.example.greenlight.model.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var testList: ArrayList<Test>
    private lateinit var questionList: ArrayList<Question>
    private lateinit var answerList: ArrayList<Answer>
    private lateinit var api: Api
    private lateinit var testAdminAdapter: TestAdminAdapter
    private lateinit var questionAdminAdapter: QuestionAdminAdapter
    private lateinit var choiceTestAdapter: ChoiceTestAdminAdapter
    private lateinit var choiceQuestionAdminAdapter: ChoiceQuestionAdminAdapter
    private lateinit var answerAdminAdapter: AnswerAdminAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api = ServiceBuilder.buildService(Api::class.java)
        testAdminAdapter = TestAdminAdapter()
        questionAdminAdapter = QuestionAdminAdapter()
        choiceTestAdapter = ChoiceTestAdminAdapter()
        choiceQuestionAdminAdapter = ChoiceQuestionAdminAdapter()
        answerAdminAdapter = AnswerAdminAdapter()


        binding.backButton.setOnClickListener {
            val intent = Intent(this@AdminActivity,MainActivity::class.java)
            startActivity(intent)
        }

        binding.createTestButton.setOnClickListener {
           val intent = Intent(this@AdminActivity, TestAdminActivity::class.java)
            startActivity(intent)
        }

        getAllQuestions()

        getAllTests()

        getAllAnswers()

        testAdminAdapter.onDeleteClick = {test ->
            deleteTest(test.testId.toString())
            getAllTests()
        }
        testAdminAdapter.onUpdateClick = {test->
            val intent = Intent(this@AdminActivity,TestUpdateAdminActivity::class.java)
            intent.putExtra("TEST_ID",test.testId)
            startActivity(intent)
        }

        binding.createQuestionButton.setOnClickListener {
          showChoiceTestDialog()
        }

        binding.createAnswerButton.setOnClickListener {
            showChoiceQuestionDialog()
        }

        questionAdminAdapter.onDeleteClick = {question->
            deleteQuestion(question.questionId.toString())
            getAllQuestions()
        }

        questionAdminAdapter.onUpdateClick = {question ->
            val intent = Intent(this@AdminActivity, QuestionUpdateAdminActivity::class.java)
            intent.putExtra("QUESTION_ID",question.questionId)
            startActivity(intent)
        }

        choiceTestAdapter.onItemClick = {test->
             val intent = Intent(this@AdminActivity, QuestionAdminActivity::class.java)
            intent.putExtra("TEST_ID",test.testId.toString())
            startActivity(intent)
        }

        choiceQuestionAdminAdapter.onItemClick = {question ->
             val intent = Intent(this@AdminActivity, AnswerAdminActivity::class.java)
            intent.putExtra("QUESTION_ID",question.questionId)
            startActivity(intent)
        }

        answerAdminAdapter.onDeleteClick = {answer ->

        api.deleteAnswer(answer.answerId.toString()).enqueue(object :Callback<Answer>{

            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                if (response.isSuccessful){

                    Toast.makeText(applicationContext, "deleted", Toast.LENGTH_SHORT).show()
                    getAllAnswers()

                }

            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
             Log.e("zxc12345",t.message.toString())
            }

        })

        }

        answerAdminAdapter.onUpdateClick = {answer ->
            val intent = Intent(this@AdminActivity,AnswerUpdateAdminActivity::class.java)
            intent.putExtra("ANSWER_ID",answer.answerId)
            startActivity(intent)
        }

    }

    private fun getAllAnswers() {
        api.getAllAnswers().enqueue(object:Callback<ApiResponse<Answer>>{
            override fun onResponse(
                call: Call<ApiResponse<Answer>>,
                response: Response<ApiResponse<Answer>>
            ) {
                   if(response.isSuccessful){
                       answerList = response.body()!!.result as ArrayList<Answer>
                       answerAdminAdapter.setAnswerList(answerList)

                       binding.answerRecyclerView.apply {
                           adapter = answerAdminAdapter
                       }
                   }
            }

            override fun onFailure(call: Call<ApiResponse<Answer>>, t: Throwable) {
                Log.e("zxc12345",t.message.toString())
            }

        })
    }

    private fun showChoiceQuestionDialog() {
            val choiceQuestionDialogBinding = ChoiceQuestionDialogBinding.inflate(layoutInflater)

             choiceQuestionDialogBinding.questionRecyclerView.apply {
                 adapter = choiceQuestionAdminAdapter
             }
        val dialog = Dialog(this)
        dialog.setContentView(choiceQuestionDialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun showChoiceTestDialog() {
        val choiceTestDialogBinding = ChoiceTestDialogBinding.inflate(layoutInflater)

        choiceTestDialogBinding.testRecyclerView.apply {
            adapter = choiceTestAdapter
        }

        val dialog = Dialog(this)
        dialog.setContentView(choiceTestDialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun deleteQuestion(id: String) {
        api.deleteQuestion(id).enqueue(object :Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "deleted", Toast.LENGTH_SHORT).show()
                    }
            }
            override fun onFailure(call: Call<Question>, t: Throwable) {
               Log.e("zxc12345",t.message.toString())
            }

        })
    }

    private fun getAllQuestions() {
        api.getAllQuestions().enqueue(object:Callback<ApiResponse<Question>>{
            override fun onResponse(
                call: Call<ApiResponse<Question>>,
                response: Response<ApiResponse<Question>>
            ) {
               if(response.isSuccessful){
                   questionList = response.body()!!.result as ArrayList<Question>
                   questionAdminAdapter.setQuestionList(questionList)
                   choiceQuestionAdminAdapter.setQuestionList(questionList)
                   binding.questionRecyclerView.apply {
                       adapter = questionAdminAdapter
                   }
               }
            }

            override fun onFailure(call: Call<ApiResponse<Question>>, t: Throwable) {
                Log.e("zxc2134",t.message.toString())
            }

        })
    }

    private fun deleteTest(id: String) {
        api.deleteTest(id).enqueue(object:Callback<Test>{
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                Toast.makeText(applicationContext, "deleted", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
                Log.e("zxc2133",t.message.toString())
            }

        })
    }

    private fun getAllTests() {
    api.getAllTests().enqueue(object:Callback<ApiResponse<Test>>{
        override fun onResponse(
            call: Call<ApiResponse<Test>>,
            response: Response<ApiResponse<Test>>
        ) {
           if(response.isSuccessful){
               testList = response.body()!!.result as ArrayList<Test>
               testAdminAdapter.setTestList(testList)
               choiceTestAdapter.setTestList(testList)
               binding.testRecyclerView.apply {
                   adapter = testAdminAdapter
               }
           }
        }

        override fun onFailure(call: Call<ApiResponse<Test>>, t: Throwable) {
            Log.e("zxc12345",t.message.toString())
        }

    })
    }
}