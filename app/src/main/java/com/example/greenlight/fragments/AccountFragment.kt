package com.example.greenlight.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.greenlight.R
import com.example.greenlight.activities.AdminActivity
import com.example.greenlight.activities.LoginActivity
import com.example.greenlight.databinding.FragmentAccountBinding
import com.example.greenlight.databinding.FragmentHomeBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userId: String
    private lateinit var role: String
    private lateinit var api: Api


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = this.requireActivity().getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        api = ServiceBuilder.buildService(Api::class.java)
        userId = sharedPreferences.getString("userId","")!!
        role = sharedPreferences.getString("role","")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(role == "2"){
            binding.adminButton.visibility = View.VISIBLE
        }

        binding.adminButton.setOnClickListener {
            val intent = Intent(this.requireActivity(),AdminActivity::class.java)
            startActivity(intent)
        }

        getUserById(userId)

        binding.deleteButton.setOnClickListener {

            deleteUserById(userId)
            Toast.makeText(this.requireActivity(), "аккаунт удален", Toast.LENGTH_SHORT).show()
            val intent = Intent(this.requireActivity(), LoginActivity::class.java)
            startActivity(intent)

        }

        binding.editButton.setOnClickListener {

            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if(login.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("login", login)
                editUserData(data)
                binding.loginEditText.text.clear()
            }

            if(password.isNotEmpty()){
                val data = JsonObject()
                data.addProperty("password", password)
                editUserData(data)
                binding.passwordEditText.text.clear()
            }

            getUserById(userId)

        }

    }

    private fun editUserData(data: JsonObject) {
     api.updateUser(userId,data).enqueue(object:Callback<User>{
         override fun onResponse(call: Call<User>, response: Response<User>) = Unit
         override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("zxc2134",t.message.toString())
         }
     })
    }

    private fun deleteUserById(userId: String) {
            api.deleteUser(userId).enqueue(object:Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) = Unit
            override fun onFailure(call: Call<User>, t: Throwable) {
               Log.e("zxc12345",t.message.toString())
            }

        })
    }

    private fun getUserById(userId: String) {
        api.getUserById(userId).enqueue(object:Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    binding.loginEditText.hint = response.body()!!.login
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
              Log.e("zxc123",t.message.toString())
            }

        })
    }

}