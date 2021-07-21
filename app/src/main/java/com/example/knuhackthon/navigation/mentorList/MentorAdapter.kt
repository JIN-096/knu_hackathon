package com.example.knuhackthon.navigation.mentorList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.databinding.MentorRecyclerBinding

class MentorAdapter: RecyclerView.Adapter<Holder>() {

    var mentorItemList = mutableListOf<MentorItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = MentorRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return mentorItemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val mentor = mentorItemList.get(position)
        holder.setMentor(mentor)
    }

}

class Holder(val binding : MentorRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvMentorName.text}", Toast.LENGTH_LONG).show()
        }
    }

    fun setMentor(mentor : MentorItem){
        binding.tvMentorName.text = mentor.name
        binding.tvMentorContent.text = mentor.content
    }
}