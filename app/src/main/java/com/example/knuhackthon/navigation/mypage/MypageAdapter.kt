package com.example.knuhackthon.navigation.mypage

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.MessageListActivity
import com.example.knuhackthon.SignInActivity
import com.example.knuhackthon.databinding.MypageRecyclerBinding
import com.example.knuhackthon.navigation.mentorList.MentorItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyPageAdapter : RecyclerView.Adapter<Holder>() {

    var menulist = mutableListOf<MyPageMenu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = MypageRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = menulist.get(position)
        holder.setMemo(menu)
    }

    override fun getItemCount(): Int {
        return menulist.size
    }
}

class Holder(val binding: MypageRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    var db : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null

    init {
        binding.root.setOnClickListener{
//            Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.menutext.text}", Toast.LENGTH_LONG).show()
            when(binding.menutext.text)
            {
                "멘토 멘티 상태 변경" -> {
                    val intent = Intent(it.context, ChangeInfoActivity::class.java)
                    ContextCompat.startActivity(it.context, intent, null)
                }
                "로그아웃" -> {
                    val intent = Intent(it.context, SignInActivity::class.java)
                    ContextCompat.startActivity(it.context, intent, null)
                }
                else -> {

                }
            }
        }
    }
    fun setMemo(menu: MyPageMenu){
        binding.imageView.setImageResource(menu.ResId)
        binding.menutext.text = menu.MenuName
    }
}