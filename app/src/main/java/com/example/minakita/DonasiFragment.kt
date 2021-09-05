package com.example.minakita

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minakita.adapter.ListAdapter
import com.example.minakita.model.Donasi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_donasi.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DonasiFragment : Fragment() {
    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var donasi:MutableList<Donasi>
    private lateinit var listAdapter: ListAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var myDataLoaderProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donasi, container, false)

        view.transaksi.setOnClickListener {
            startActivity(Intent(context, TransaksiActivity::class.java))
        }

        mRecyclerView = view.findViewById(R.id.mRecyclerView)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        donasi = ArrayList()
        listAdapter = ListAdapter(this,donasi)
        mRecyclerView.adapter = listAdapter


        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Data_Donasi")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,error.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                donasi.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Donasi::class.java)
                    upload!!.key = teacherSnapshot.key
                    donasi.add(upload)

                }
                listAdapter.notifyDataSetChanged()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DealsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DonasiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}