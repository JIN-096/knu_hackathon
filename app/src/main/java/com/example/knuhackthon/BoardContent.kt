package com.example.knuhackthon

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knuhackthon.databinding.ActivityBoardContentBinding
import com.example.knuhackthon.databinding.ItemBoardCommentBinding
import com.example.knuhackthon.navigation.board.BoardItem
import com.example.knuhackthon.navigation.board.Holder
import com.example.knuhackthon.navigation.mypage.MyPageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.format.DateTimeFormatter

class BoardContent : AppCompatActivity() {
    val binding by lazy { ActivityBoardContentBinding.inflate(layoutInflater) }
    var db = FirebaseFirestore.getInstance()
    var auth: FirebaseAuth? = null
    var comments: ArrayList<BoardItem.Comment> = arrayListOf()
    companion object {
        var data: BoardItem? = null
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db.collection("contents").get().addOnSuccessListener {
            for (document in it) {
                if (document.data.get("bid").toString().equals(intent.getStringExtra("contents"))) {
//                    Log.d("BOARDCONTENTS", "onCreate: " + document.toString())
                    data = document.toObject(BoardItem::class.java)
                    binding.ctvAuthor.text = data!!.author
                    binding.ctvTitle.text = data!!.title
                    binding.ctvDate.text = data!!.date
                    binding.ctvContent.text = data!!.content
                    binding.ctvCommentCnt.text = data!!.CommentCnt.toString()
                    Log.d("cmt", "db "+data.toString())
                    init()
                    break;
                }
            }
        }

        binding.btnCtv.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            var comment = BoardItem.Comment()
            db.collection("users").get().addOnSuccessListener {
                for (document in it) {
                    if (document.data.get("uid")!!.equals(auth!!.uid)) {
                        comment.comment = binding.textComment.text.toString()
                        comment.name = document.data.get("name") as String
                        comment.uid = auth!!.uid.toString()
                        comment.timestamp = System.currentTimeMillis() //LocalDate.now().format(DateTimeFormatter.ISO_DATE).toString()
                        db.collection("contents").document(data?.bid.toString())
                            .collection("comments").document().set(comment)
                        binding.textComment.text = null
                        init()
                        break;
                    }
                }
            }
        }


//            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                comments.clear()
//                if (querySnapshot == null) return@addSnapshotListener
//                for (snapshot in querySnapshot.documents) {
//                    comments.add(snapshot.toObject(BoardItem.Comment::class.java)!!)
//                }
//                //notifyDataSetChanged()
//            }



    }
    fun init(){
        comments.clear();
        db.collection("contents").document(data?.bid.toString()).collection("comments")
            .orderBy("timestamp").get().addOnSuccessListener {
                for (document in it) {
                    Log.d("cmt", "onCreate: " + document.toString())
                    comments.add(document.toObject(BoardItem.Comment::class.java))
                }

                var adapter = CommentRecyclerviewAdapter()
                Log.d("check", "init: " + comments.size)
                adapter.comments = comments

                val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
                binding.ctvRecycler.addItemDecoration(decoration)

                binding.ctvRecycler.adapter = adapter
                binding.ctvRecycler.layoutManager = LinearLayoutManager(this)
            }
    }
}
