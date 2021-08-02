package com.example.minakita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    var btnNext: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnNext = findViewById(R.id.profile)

        btnNext?.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        }
    }
}