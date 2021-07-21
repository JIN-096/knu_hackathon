package com.example.knuhackthon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.knuhackthon.databinding.ActivityBoardContentBinding

class BoardContent : AppCompatActivity() {
    val binding by lazy { ActivityBoardContentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_content)
        binding.ctvContent.text = intent.getStringExtra("contents")

        Log.d("jeongjin", "onCreate: "+intent.getStringExtra("contents"))
    }
}