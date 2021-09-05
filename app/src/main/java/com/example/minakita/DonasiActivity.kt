package com.example.minakita

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import com.example.minakita.model.Donasi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_donasi.*

class DonasiActivity : AppCompatActivity() {
    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var nama : TextView
    private lateinit var instansi : TextView
    private lateinit var Media : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi)
        /**set data*/

        mStorageRef = FirebaseStorage.getInstance().getReference("Data_Donasi")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Data_Donasi")

        nama = findViewById(R.id.nameEditText)
        instansi = findViewById(R.id.namainstansiEditText)
        Media = findViewById(R.id.meediasosialEditText)

        button_choose_image.setOnClickListener { openFileChoose() }
        upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@DonasiActivity,
                        "An Upload is Still in Progress",
                        Toast.LENGTH_SHORT).show()
            }
            else{
                uploadFile()
            }
        }

    }
    private fun openFileChoose() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            chooseImageView.setImageURI(mImageUri)
        }
    }
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null) {
            val fileReference = mStorageRef!!.child(
                    System.currentTimeMillis()
                            .toString() + "." + getFileExtension(mImageUri!!)

            )
            openImagesActivity()
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true

            val nama = nama.text.toString()
            val instansi = instansi.text.toString()
            val Media = Media.text.toString()
            val ktp = mImageUri.toString()

            val intent = Intent(this@DonasiActivity, DonasiLanjutActivity::class.java)
            intent.putExtra("Nama", nama)
            intent.putExtra("Instansi", instansi)
            intent.putExtra("Media", Media)
            intent.putExtra("KTP", ktp)
            startActivity(intent)

        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun  openImagesActivity() {
        startActivity(Intent(this@DonasiActivity, DonasiLanjutActivity::class.java))
    }
}