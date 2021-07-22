package com.example.knuhackthon.navigation.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.knuhackthon.databinding.ActivityChangeInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChangeInfoActivity : AppCompatActivity() {

    val binding by lazy { ActivityChangeInfoBinding.inflate(layoutInflater) }
    var db : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var type : String? = null
    var uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        db!!.collection("users").get().addOnSuccessListener {
            Log.d("info","가져오기 성공")
            for (document in it) {
                Log.d("loadData",document.data.get("uid")!!.toString())
                Log.d("loadData",auth?.currentUser?.uid.toString())
                if(document.data.get("uid")!!.equals(auth?.currentUser?.uid.toString())){
                    uid = auth?.currentUser?.uid.toString()
                    type = document.data.get("type")!!.toString()
                    Log.d("loadData",type!!.toString())
                    if(type.equals("1")){
                        binding.tvUserInfo.setText("현재 당신은 멘토입니다")
                    }
                    else if(type.equals("0")){
                        binding.tvUserInfo.setText("현재 당신은 멘티입니다")
                    }
                    break
                }
            }
        }


        binding.btnOk.setOnClickListener {
            val ref = db?.collection("users")?.document(auth?.currentUser?.uid.toString())
            if(type.equals("1"))
            {
                db?.runTransaction { transaction ->
                    val snapshot = transaction.get(ref!!)
                    val newType = "0"
                    transaction.update(ref,"type",newType)
                }?.addOnSuccessListener {
                    Toast.makeText(this,"변경 성공",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else
            {
                db?.runTransaction { transaction ->
                    val snapshot = transaction.get(ref!!)
                    val newType = "1"
                    transaction.update(ref,"type",newType)
                }?.addOnSuccessListener {
                    Toast.makeText(this,"변경 성공",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        }

    }
}