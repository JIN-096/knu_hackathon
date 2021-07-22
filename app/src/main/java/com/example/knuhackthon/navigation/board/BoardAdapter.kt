package com.example.knuhackthon.navigation.board

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.BoardContent
import com.example.knuhackthon.databinding.BoardRecyclerBinding
import com.example.knuhackthon.databinding.ItemBoardCommentBinding

class BoardAdapter: RecyclerView.Adapter<Holder>() {
    var boardItemList = mutableListOf<BoardItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = BoardRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return boardItemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = boardItemList.get(position)
        holder.setBoard(item)
    }
}

class Holder(val binding: BoardRecyclerBinding): RecyclerView.ViewHolder(binding.root){
    init {
        binding.root.setOnClickListener {
            val intent = Intent(it.context, BoardContent::class.java)
            Toast.makeText(binding.root.context,"클릭된 아이템 = ${binding.tvTitle.text}",Toast.LENGTH_LONG).show()
            intent.putExtra("contents",binding.tvBid.text.toString())
            startActivity(it.context,intent,null)
        }
    }

    fun setBoard(item : BoardItem){
        binding.tvBid.text = item.bid.toString()
        binding.tvTitle.text = item.title
        binding.tvAuthor.text = item.author
        binding.tvContent.text = item.content
        binding.tvDate.text = item.date
    }
}