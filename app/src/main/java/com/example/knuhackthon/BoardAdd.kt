package com.example.knuhackthon

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.knuhackthon.databinding.ActivityBoardAddBinding
import com.example.knuhackthon.navigation.board.BoardFragment
import com.example.knuhackthon.navigation.board.BoardItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BoardAdd : AppCompatActivity() {

    val binding by lazy { ActivityBoardAddBinding.inflate(layoutInflater) }
    var auth : FirebaseAuth? = null
    var db = FirebaseFirestore.getInstance()
    lateinit var name : String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db.collection("users").get().addOnSuccessListener {
            for (document in it) {
                if(document.data.get("uid")!!.equals(auth!!.uid)){
                    name = document.data.get("name") as String
                    break;
                }
            }
        }

        binding.baBtnBack.setOnClickListener {
            finish()
        }


        binding.baBtnCommit.setOnClickListener {

            val data = BoardItem(
                name,
                binding.baTitle.text.toString(),
                binding.baContent.text.toString(),
                LocalDate.now().format(DateTimeFormatter.ISO_DATE).toString(),
                auth!!.uid.toString(),
                intent.getIntExtra("bid",0)
            )
            db.collection("contents").document(intent.getIntExtra("bid",0).toString()).set(data).addOnSuccessListener {
                finish()
            }
//            Toast.makeText(this,data.toString(),Toast.LENGTH_LONG).show()
        }

    }
}