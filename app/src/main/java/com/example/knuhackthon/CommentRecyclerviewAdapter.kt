package com.example.knuhackthon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.BoardContent.Companion.data
import com.example.knuhackthon.databinding.ItemBoardCommentBinding
import com.example.knuhackthon.navigation.board.BoardItem
import com.example.knuhackthon.navigation.mypage.MyPageMenu
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList

class CommentRecyclerviewAdapter: RecyclerView.Adapter<Holder>() {
    var comments: ArrayList<BoardItem.Comment> = arrayListOf()
    var db = FirebaseFirestore.getInstance()

    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBoardCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cmt = comments.get(position)
        holder.setComment(cmt)
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}

class Holder(val binding : ItemBoardCommentBinding) : RecyclerView.ViewHolder(binding.root){

    fun setComment(cmt: BoardItem.Comment){
        binding.cmtContent.text = cmt.comment
        binding.cmtName.text = cmt.name
        binding.cmtDate.text = SimpleDateFormat("yyyy-MM-dd").format(Date(cmt.timestamp!!))
    }

}