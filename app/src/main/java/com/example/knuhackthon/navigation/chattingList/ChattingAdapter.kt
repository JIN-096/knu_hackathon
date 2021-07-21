package com.example.knuhackthon.navigation.chattingList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.databinding.ChattingRecyclerBinding
import com.example.knuhackthon.databinding.MentorRecyclerBinding
import com.example.knuhackthon.navigation.mentorList.MentorItem

class ChattingAdapter:RecyclerView.Adapter<Holder>() {

    var chattingItemList = mutableListOf<ChattingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ChattingRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return chattingItemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val chat = chattingItemList.get(position)
        holder.setChatting(chat)
    }
}

class Holder(val binding : ChattingRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvChattingOtherName.text}", Toast.LENGTH_LONG).show()
        }
    }

    fun setChatting(chat : ChattingItem){
        binding.tvChattingOtherName.text = chat.name
        binding.tvChattingContent.text = chat.content
    }

}