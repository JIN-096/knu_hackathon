package com.example.knuhackthon

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.databinding.ActivityMessageListBinding
import com.example.knuhackthon.navigation.mentorList.MentorItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MessageListActivity : AppCompatActivity() {

    val binding by lazy { ActivityMessageListBinding.inflate(layoutInflater) }
    val messageList = mutableListOf<Message>()

    var db : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var name : String? = null
    private lateinit var database: DatabaseReference

    var curUid : String? = null
    var uid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = intent.getStringExtra("uid")
        curUid = auth?.currentUser?.uid.toString()
        db = FirebaseFirestore.getInstance()
        db!!.collection("users").get().addOnSuccessListener {
            for (document in it) {
                if(document.data.get("uid")!!.equals(curUid)){
                    name = document.data.get("name").toString()
                    break
                }
            }
        }

        setSupportActionBar(binding.toolbarGchannel)
        supportActionBar?.apply {
            title = intent.getStringExtra("name")+" 멘토와의 대화방"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val adapter = MessageAdapter()
        binding.recyclerChat.adapter = adapter
        binding.recyclerChat.layoutManager = LinearLayoutManager(this)


        database = Firebase.database.getReference(uid!!)

        binding.buttonGchatSend.setOnClickListener {
            // 현재시간을 가져오기
            val now: Long = System.currentTimeMillis()
            val date = Date(now)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
            val stringTime = dateFormat.format(date)

            val message = Message(binding.editGchatMessage.text.toString(),stringTime.substring(11,16),name,auth?.currentUser?.uid.toString())
            database.push().setValue(message)
            binding.editGchatMessage.setText("")
        }

        // Read from the database
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                messageList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    Log.d("snapshot","check")
                    Log.d("message",postSnapshot.child("message").getValue().toString())
                    Log.d("creat",postSnapshot.child("createdAt").getValue().toString())
                    Log.d("name",postSnapshot.child("name").getValue().toString())

                    messageList.add(Message(postSnapshot.child("message").getValue().toString(),postSnapshot.child("createdAt").getValue().toString(),
                        postSnapshot.child("name").getValue().toString(),
                        postSnapshot.child("uid").getValue().toString()))
                }
                adapter.messageList = messageList
                adapter.notifyDataSetChanged()
                binding.recyclerChat.scrollToPosition(messageList.size-1)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("message", "Failed to read value.", error.toException())
            }
        })


        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {


            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.addChildEventListener(childEventListener)
    }

}