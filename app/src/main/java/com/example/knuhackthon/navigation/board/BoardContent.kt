package com.example.knuhackthon.navigation.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.knuhackthon.databinding.ActivityBoardContentBinding

class BoardContent : AppCompatActivity() {
    val binding by lazy { ActivityBoardContentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ctvContent.text = intent.getStringExtra("contents").toString()

        Log.d("jeongjin", "onCreate: "+intent.getStringExtra("contents").toString())
    }
}