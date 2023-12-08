package com.example.greenlight.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityLoginBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.fragments.AccountFragment
import com.example.greenlight.fragments.HomeFragment
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regButton.setOnClickListener {
            val intent = Intent(this@LoginActivity,RegActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if(login.isNotEmpty() && password.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("login",login)
                data.addProperty("password",password)
                auth(data)
            }
        }
    }

    private fun auth(data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.login(data).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){

                    val sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharePreference.edit()

                    editor.putString("userId",response.body()!!.userId)
                    editor.putString("role",response.body()!!.role)
                    editor.apply()

                    Toast.makeText(applicationContext, "auth success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("zcx123",t.message.toString())
            }

        })
    }
}