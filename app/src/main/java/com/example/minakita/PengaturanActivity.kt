package com.example.minakita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_pengaturan.*
import kotlinx.android.synthetic.main.fragment_setting.*

class PengaturanActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.authState.observe(this, { userInfo(it) })

    }

    private fun userInfo(user: FirebaseUser?){
        if (user == null){
            tv_user.text = "USERNAME"
            t_isi_username.visibility = View.GONE
            t_isi_email.visibility = View.GONE
            t_isi_password.visibility = View.GONE
        }
        else{
            auth = FirebaseAuth.getInstance()
            firebaseUser = FirebaseAuth.getInstance().currentUser!!

            val userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(firebaseUser.uid)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val user =snapshot.getValue<User>(User::class.java)
                        nama.text = user!!.nama
                        t_isi_email.text = firebaseUser.email
                        t_isi_username.text = user!!.username
                        t_isi_password.text = user!!.password
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}