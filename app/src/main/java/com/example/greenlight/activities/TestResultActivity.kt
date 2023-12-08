package com.example.greenlight.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenlight.R
import com.example.greenlight.databinding.ActivityTestResultBinding

class TestResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val totalPoints = intent.getStringExtra("TOTAL_POINTS")

        binding.resultText.text = "Вы набрали ${totalPoints.toString()} очка(ов)! \nЭто успех"

        binding.endButton.setOnClickListener {
            val intent = Intent(this@TestResultActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}