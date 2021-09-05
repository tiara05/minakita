package com.example.minakita

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_pengaturan.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.logout
import kotlinx.android.synthetic.main.fragment_setting.view.*
import java.util.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 101
    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.authState.observe(this, { userInfo(it) })

        view.profile.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        view.notifikasi.setOnClickListener {
            startActivity(Intent(context, NotifikasiActivity::class.java))
        }

        view.pengaturan.setOnClickListener {
            startActivity(Intent(context, PengaturanActivity::class.java))
        }

        view.tentang.setOnClickListener {
            startActivity(Intent(context, TentangActivity::class.java))
        }

        view.faq.setOnClickListener {
            startActivity(Intent(context, FaqActivity::class.java))
        }

        view.logout.setOnClickListener{
            if (logout.text == getString(R.string.logout)) {
                auth.signOut()

            }
            else{
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }


        return view
    }

    private fun BottomSheetDialog(settingFragment: SettingFragment): BottomSheetDialog {
        TODO("Not yet implemented")
    }

    private fun userInfo(user: FirebaseUser?){
        if (user == null){
            nama.text = "USERNAME"
            logout.text = getString(R.string.login)
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
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}