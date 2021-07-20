package com.example.knuhackthon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.knuhackthon.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    var auth : FirebaseAuth? = null
    var email : String? = null
    var pw : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        email = intent.getStringExtra("email")
        pw = intent.getStringExtra("pw")

        binding.btnSignin.setOnClickListener {
            auth?.signInWithEmailAndPassword(email!!,pw!!)
                ?.addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        /*if(isEmailVerified()){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            auth?.signOut()
                            Toast.makeText(this,"인증메일을 확인해주세요",Toast.LENGTH_LONG).show()
                        }*/

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    /*fun isEmailVerified(){
        var user = auth?.currentUser
        if(user!=null){
            var accessToken =
        }
    }*/
}