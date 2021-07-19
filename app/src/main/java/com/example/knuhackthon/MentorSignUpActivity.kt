package com.example.knuhackthon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.knuhackthon.databinding.ActivityMentorSignUpBinding
import com.example.knuhackthon.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MentorSignUpActivity : AppCompatActivity() {

    val binding by lazy { ActivityMentorSignUpBinding.inflate(layoutInflater) }
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener { signUp() }
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun signUp(){
        auth?.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.pwText.text.toString())
            ?.addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"계정 생성 성공. 검증용 이메일 전송",Toast.LENGTH_LONG).show()
                    sendVerificationEmail()
                }
                else{

                }
            }
    }

    fun sendVerificationEmail(){
        var user = auth?.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"인증메일이 전송되었습니다. 메일을 확인해 주세요",Toast.LENGTH_LONG).show()
                auth?.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("email",binding.emailText.text.toString())
                intent.putExtra("pw", binding.pwText.text.toString())
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"검증 메일 전송 실패했습니다.",Toast.LENGTH_LONG).show()
            }
        }
        Toast.makeText(this,"계정 생성 성공. 검증용 이메일 전송",Toast.LENGTH_LONG).show()
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}