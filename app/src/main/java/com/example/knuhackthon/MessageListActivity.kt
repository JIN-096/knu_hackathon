package com.example.knuhackthon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.databinding.ActivityMessageListBinding

class MessageListActivity : AppCompatActivity() {

    val binding by lazy { ActivityMessageListBinding.inflate(layoutInflater) }
    val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarGchannel)

        supportActionBar?.apply {
            title = intent.getStringExtra("name")+" 멘토와의 대화방"

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val adapter = MessageAdapter()
        loadMessage()
        adapter.messageList = messageList

        binding.recyclerChat.adapter = adapter
        binding.recyclerChat.layoutManager = LinearLayoutManager(this)
    }

    fun loadMessage(){
        messageList.add(Message("ㅎㅇ1","12:29",User("천지완","url1")))
        messageList.add(Message("ㅎㅇ2","12:30",User("이준하","url2")))
        messageList.add(Message("ㅎㅇ3","12:31",User("이정진","url3")))
        messageList.add(Message("ㅎㅇ4","12:32",User("천지완","url4")))
        messageList.add(Message("ㅎㅇ5","12:33",User("이준하","url5")))
        messageList.add(Message("ㅎㅇ6","12:34",User("이정진","url6")))
    }
}