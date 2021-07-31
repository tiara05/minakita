package com.example.internshipminakita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Onboarding3Activity : AppCompatActivity() {

    var btnNext: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding3)

        btnNext = findViewById(R.id.btn_next)

        btnNext?.setOnClickListener {
            startActivity(Intent(this@Onboarding3Activity, RegisterActivity::class.java))
        }
    }
}