package com.example.minakita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minakita.uitel.loadImage
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intss = intent
        var nameT = intss.getStringExtra("NAMET")
        var desT = intss.getStringExtra("DESCRIT")
        var target = intss.getStringExtra("Target")
        var imgT = intss.getStringExtra("IMGURI")
        var har = intss.getStringExtra("hari")
        var don = intss.getStringExtra("donatur")

        nameDetailTextView.text = nameT
        total.text = target
        teacherDetailImageView.loadImage(imgT)
        deskripsiEditText.text = desT
        hari.text = har
        donaturTV.text = don
    }

}