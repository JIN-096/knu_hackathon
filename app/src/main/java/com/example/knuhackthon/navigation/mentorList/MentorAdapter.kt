package com.example.knuhackthon.navigation.mentorList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knuhackthon.MessageListActivity
import com.example.knuhackthon.databinding.MentorRecyclerBinding
import com.google.firebase.firestore.FirebaseFirestore


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
    var db : FirebaseFirestore? = null

    init {
        binding.root.setOnClickListener {
//            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvMentorName.text}", Toast.LENGTH_LONG).show()
            val intent = Intent(it.context, MessageListActivity::class.java)
            intent.putExtra("name",binding.tvMentorName.text.toString())
            intent.putExtra("uid",binding.tvMentorUid.text.toString())
            startActivity(it.context,intent,null)
        }
    }

    fun setMentor(mentor : MentorItem){
        binding.tvMentorName.text = mentor.name
        binding.tvMentorBelong.text = mentor.belong
        binding.tvMentorSpec.text = mentor.spec
        binding.tvMentorUid.text = mentor.uid

        db = FirebaseFirestore.getInstance()
        db!!.collection("profileImages").get().addOnSuccessListener {
            for (document in it){
                if(document.id.equals(mentor.uid.toString())){
                    var url = document.data.get("image").toString()
                    Glide.with(binding.imgMentorProfile).load(url).into(binding.imgMentorProfile)
                }
            }
        }

    }
}