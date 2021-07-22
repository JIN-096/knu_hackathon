package com.example.knuhackthon

import android.text.format.DateUtils.formatDateTime
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.databinding.ItemChatMeBinding
import com.example.knuhackthon.databinding.ItemChatOtherBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.installations.Utils
import java.text.SimpleDateFormat

class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    var messageList = mutableListOf<Message>()

    inner class SentMessageHolder(val binding: ItemChatMeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message : Message){
            binding.tvMessageMe.text = message.message
            binding.tvTimestampMe.text = message.createdAt
        }
    }

    inner class ReceivedMessageHolder (val binding: ItemChatOtherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message : Message){
            binding.tvMessageOther.text = message.message
            binding.tvTimestampOther.text = message.createdAt
            binding.tvNameOther.text = message.name
        }
    }

    override fun getItemViewType(position: Int): Int {
        var auth = FirebaseAuth.getInstance()
        Log.d("uid",position.toString())
        Log.d("uid",auth.currentUser?.uid.toString())
        Log.d("uid",messageList.get(position).uid + messageList.get(position).message)

        if(auth.currentUser?.uid.toString().equals(messageList[position].uid))
            return VIEW_TYPE_MESSAGE_SENT
        else
            return VIEW_TYPE_MESSAGE_RECEIVED

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_MESSAGE_SENT -> {
                val binding = ItemChatMeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                SentMessageHolder(binding)
            }
            else -> {
                val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ReceivedMessageHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SentMessageHolder){
            holder.bind(messageList.get(position))
        }
        else if(holder is ReceivedMessageHolder){
            holder.bind(messageList.get(position))
        }
    }
}

