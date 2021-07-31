package com.example.internshipminakita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.internshipminakita.databinding.ActivityRegisterBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var database : DatabaseReference

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        google.setOnClickListener { mulaiLogin() }

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.registerBtn.setOnClickListener {

            val firstName = binding.nama.text.toString()
            val lastName = binding.email.text.toString()
            val age = binding.username.text.toString()
            val userName = binding.password.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val User = User(firstName,lastName,age,userName)
            database.child(userName).setValue(User).addOnSuccessListener {

                binding.nama.text.clear()
                binding.email.text.clear()
                binding.username.text.clear()
                binding.password.text.clear()

                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mulaiLogin() {
        if (google.text == getString(R.string.logout)) {
            AuthUI.getInstance().signOut(this)
            return
        }
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        startActivityForResult(intent, RegisterActivity.SIGN_IN_REQUEST_CODE)
    }
}