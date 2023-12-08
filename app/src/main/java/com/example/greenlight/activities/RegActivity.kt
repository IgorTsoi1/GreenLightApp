package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityRegBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this@RegActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.createButton.setOnClickListener {

            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if(login.isNotEmpty() && password.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("login",login)
                data.addProperty("password",password)
                reg(data)
            }

        }

    }

    private fun reg(data: JsonObject) {
      val api = ServiceBuilder.buildService(Api::class.java)
        api.registration(data).enqueue(object:Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                      if(response.isSuccessful){
                          Toast.makeText(applicationContext, "registration success", Toast.LENGTH_SHORT).show()
                          val intent = Intent(this@RegActivity, LoginActivity::class.java)
                          startActivity(intent)
                      }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("zxc123",t.message.toString()
                )
            }

        })
    }
}