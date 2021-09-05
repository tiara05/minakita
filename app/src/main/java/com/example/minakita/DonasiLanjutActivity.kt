package com.example.minakita

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.minakita.model.Donasi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_donasi.*
import kotlinx.android.synthetic.main.activity_donasi.button_choose_image
import kotlinx.android.synthetic.main.activity_donasi.chooseImageView
import kotlinx.android.synthetic.main.activity_donasi.progressBar
import kotlinx.android.synthetic.main.activity_donasi.upLoadBtn
import kotlinx.android.synthetic.main.activity_donasi_lanjut.*
import org.w3c.dom.Text

class DonasiLanjutActivity : AppCompatActivity() {
    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var name : TextView
    private lateinit var instansii : TextView
    private lateinit var akun : TextView
    private lateinit var ktp : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donasi_lanjut)
        /**set data*/

        mStorageRef = FirebaseStorage.getInstance().getReference("Data_Donasi")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Data_Donasi")

        val intent = intent
        val nama = intent.getStringExtra("Nama")
        val instansi = intent.getStringExtra("Instansi")
        val media = intent.getStringExtra("Media")
        val ktpp = intent.getStringExtra("KTP")


        name = findViewById(R.id.nameEditText)
        instansii = findViewById(R.id.namainstansiEditText)
        akun = findViewById(R.id.meediasosialEditText)
        ktp = findViewById(R.id.chooseImageViewktp)

        name.text = nama
        instansii.text = instansi
        akun.text = media
        ktp.text = ktpp

        button_choose_image.setOnClickListener { openFileChoose() }
        upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@DonasiLanjutActivity,
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
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    val handler = Handler()
                    handler.postDelayed({
                        progressBar.visibility = View.VISIBLE
                        progressBar.isIndeterminate = false
                        progressBar.progress = 0
                    }, 500)
                    Toast.makeText(
                        this@DonasiLanjutActivity,
                        "Data Donatur Upload successful",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    val upload = Donasi(
                        namadonasi = donasiEditText!!.text.toString().trim { it <= ' ' },
                        imageUrlDonasi = mImageUri.toString(),
                        deskripsi =  deskripsiEditText!!.text.toString().trim { it <= ' ' },
                        target =  targetEditText!!.text.toString().trim { it <= ' ' },
                        batas =  bataswaktiEditText!!.text.toString().trim { it <= ' ' },
                        penerima =  penerimaiEditText!!.text.toString().trim { it <= ' ' },
                        name = name.text.toString().trim { it <= ' ' },
                        namaInstansi = instansii.text.toString().trim { it <= ' ' },
                        MediaSosial = akun.text.toString().trim { it <= ' ' },
                        imageUrlKtp = ktp.text.toString().trim { it <= ' ' }
                    )
                    val uploadId = mDatabaseRef!!.push().key
                    mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    progressBar.visibility = View.INVISIBLE
                    openImagesActivity()
                }
                .addOnFailureListener { e ->
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@DonasiLanjutActivity, e.message, Toast.LENGTH_SHORT).show()
                    Log.e("data","${e.message}")
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressBar.progress = progress.toInt()
                }
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun  openImagesActivity() {
        startActivity(Intent(this@DonasiLanjutActivity, HomeActivity::class.java))
    }
}