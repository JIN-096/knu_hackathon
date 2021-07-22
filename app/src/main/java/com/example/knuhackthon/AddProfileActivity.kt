package com.example.knuhackthon

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.knuhackthon.databinding.ActivityAddProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddProfileActivity : AppCompatActivity() {

    val binding by lazy { ActivityAddProfileBinding.inflate(layoutInflater) }
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var db  : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.addImage.setOnClickListener {
            // Open the album
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
        }

        binding.nextpage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode== Activity.RESULT_OK){
                // This is path to the selected image
                photoUri = data?.data
                binding.profileImage.setImageURI(photoUri)

                var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                var imageFileName = "IMAGE_" + timestamp + "_.png"

                var storageRef = storage?.reference?.child("images")?.child(imageFileName)
                storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
                    Toast.makeText(this,"put file 성공",Toast.LENGTH_SHORT).show()
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        var map = HashMap<String, Any>()
                        var uid = FirebaseAuth.getInstance().currentUser?.uid
                        map["image"] = uri.toString()
                        db?.collection("profileImages")?.document(uid!!)?.set(map)

                        setResult(Activity.RESULT_OK)
                    }
                }

            }else{
                //Exit the addPhotoActivity if you leave the album without selecting it
                Toast.makeText(this,"실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}