package com.example.minakita

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minakita.databinding.ActivityRegisterBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    lateinit var ref: DatabaseReference

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 101
    }

    private var cek = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("USERS")

        google.setOnClickListener {
            mulaiLogin()
        }

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.authState.observe(this, Observer { updateUI(it) })

        binding.registerBtn.setOnClickListener {
            val nama = binding.nama.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if(nama.isEmpty()){
                binding.nama.error = "Nama required"
                binding.nama.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty() || password.length < 6){
                binding.password.error = "Password required"
                binding.password.requestFocus()
                return@setOnClickListener
            }
            if(email.isEmpty()){
                binding.email.error = "email required"
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if(username.isEmpty()){
                binding.username.error = "Username Required"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            registrasiUser(email, password, nama, username)

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {

        }
        else {
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
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

    private fun registrasiUser(email: String, password: String, nama: String, username: String){
        val progressDialog = ProgressDialog(this@RegisterActivity)
        progressDialog.setTitle("Registratation User")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                saveUser(email, nama, username, password, progressDialog)
                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
            }else{
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser(email: String,
                         nama: String,
                         username: String,
                         password: String,
                         progressDialog: ProgressDialog) {
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child("USERS")
        val userMap = HashMap<String, Any>()
        userMap["id"] = currentUserId
        userMap["email"] = email
        userMap["username"] = username
        userMap["password"] = password
        userMap["nama"] = nama

        ref.child(currentUserId).setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful) {
//                Toast.makeText(this, "Register is Successfully", Toast.LENGTH_SHORT).show()
            } else {
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }
}