package com.example.greenlight.interfaces

import com.example.greenlight.model.Answer
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Question
import com.example.greenlight.model.Test
import com.example.greenlight.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    //User
    @Headers("Content-Type:application/json")
    @POST("user/login")
    fun login(
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/create")
    fun registration(
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/{userId}")
    fun getUserById(
        @Path("userId") id: String
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/update/{userId}")
    fun updateUser(
        @Path("userId") id : String,
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/delete/{userId}")
    fun deleteUser(
        @Path("userId") id : String
    ): Call<User>

    //Test
    @Headers("Content-Type:application/json")
    @POST("test/create")
    fun createTest(
        @Body body: JsonObject
    ): Call<Test>

    @Headers("Content-Type:application/json")
    @POST("test/all")
    fun  getAllTests(
    ): Call<ApiResponse<Test>>

    @Headers("Content-Type:application/json")
    @POST("test/find/{testId}")
    fun findTestById(
        @Path("testId") id : String
    ): Call<ApiResponse<Test>>

    @Headers("Content-Type:application/json")
    @POST("test/delete/{testId}")
    fun deleteTest(
        @Path("testId") id : String
    ): Call<Test>

    @Headers("Content-Type:application/json")
    @POST("test/update/{testId}")
    fun updateTest(
        @Path("testId") id : String,
        @Body body: JsonObject
    ): Call<Test>

    //Question
    @Headers("Content-Type:application/json")
    @POST("question")
    fun getAllQuestions(
    ): Call<ApiResponse<Question>>

    @Headers("Content-Type:application/json")
    @POST("question/create")
    fun createQuestion(
        @Body body: JsonObject
    ): Call<Question>

    @Headers("Content-Type:application/json")
    @POST("question/find/{testId}")
    fun findQuestionByTest(
        @Path("testId") id : String
    ): Call<ApiResponse<Question>>

    @Headers("Content-Type:application/json")
    @POST("question/{questionId}")
    fun getQuestionById(
        @Path("questionId") id : String
    ): Call<Question>

    @Headers("Content-Type:application/json")
    @POST("question/update/{questionId}")
    fun updateQuestion(
        @Path("questionId") id: String,
        @Body body: JsonObject
    ): Call<ApiResponse<Question>>

    @Headers("Content-Type:application/json")
    @POST("question/delete/{questionId}")
    fun deleteQuestion(
        @Path("questionId") id : String
    ): Call<Question>

    //Answer
    @Headers("Content-Type:application/json")
    @POST("answer")
    fun getAllAnswers(

    ): Call<ApiResponse<Answer>>

    @Headers("Content-Type:application/json")
    @POST("answer/create")
    fun createAnswers(
     @Body body: JsonObject
    ): Call<ApiResponse<Answer>>

    @Headers("Content-Type:application/json")
    @POST("answer/find/{questionId}")
    fun getAnswersByQuestionId(
        @Path("questionId") id : String
    ): Call<ApiResponse<Answer>>

    @Headers("Content-Type:application/json")
    @POST("answer/{answerId}")
    fun getAnswerById(
        @Path("answerId") id : String
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("answer/update/{answerId}")
    fun updateAnswer(
        @Path("answerId") id : String,
        @Body body: JsonObject
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("answer/delete/{answerId}")
    fun deleteAnswer(
        @Path("answerId") id : String,
    ): Call<Answer>


}




//██░▀██████████████▀░██   Этот кот охраняет этот код xd
//█▌▒▒░████████████░▒▒▐█
//█░▒▒▒░██████████░▒▒▒░█
//▌░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▐
//░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░
//███▀▀▀██▄▒▒▒▒▒▒▒▄██▀▀▀██
//██░░░▐█░▀█▒▒▒▒▒█▀░█▌░░░█
//▐▌░░░▐▄▌░▐▌▒▒▒▐▌░▐▄▌░░▐▌
//█░░░▐█▌░░▌▒▒▒▐░░▐█▌░░█
//▒▀▄▄▄█▄▄▄▌░▄░▐▄▄▄█▄▄▀▒
//░░░░░░░░░░└┴┘░░░░░░░░░
//██▄▄░░░░░░░░░░░░░░▄▄██
//████████▒▒▒▒▒▒████████
//█▀░░███▒▒░░▒░░▒▀██████
//█▒░███▒▒╖░░╥░░╓▒▐█████
//█▒░▀▀▀░░║░░║░░║░░█████
//██▄▄▄▄▀▀┴┴╚╧╧╝╧╧╝┴┴███
//██████████████████████