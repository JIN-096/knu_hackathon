package com.example.knuhackthon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.knuhackthon.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnMentorSignup.setOnClickListener {
            val intent = Intent(this, MentorSignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnMenteeSignup.setOnClickListener {
            val intent = Intent(this, MenteeSignUpActivity::class.java)
            startActivity(intent)
        }
    }
}