package com.example.knuhackthon.navigation.mentorList

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.R
import com.example.knuhackthon.databinding.FragmentMentorListBinding
import com.example.knuhackthon.databinding.FragmentMypageBinding
import com.example.knuhackthon.navigation.board.BoardItem
import com.example.knuhackthon.navigation.mypage.MyPageAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects

class MentorListFragment : Fragment() {

    companion object{
        fun newInstance() = MentorListFragment()
    }

    val mentorItemList = mutableListOf<MentorItem>()
    var db : FirebaseFirestore? = null

    private lateinit var mentorListFragmentBinding: FragmentMentorListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mentorListFragmentBinding = FragmentMentorListBinding.inflate(inflater,container,false)
        return mentorListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMentor()
        var adapter = MentorAdapter()
        adapter.mentorItemList = mentorItemList

        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        mentorListFragmentBinding.mentorlistRecycler.addItemDecoration(decoration)


        mentorListFragmentBinding.mentorlistRecycler.adapter = adapter
        mentorListFragmentBinding.mentorlistRecycler.layoutManager = LinearLayoutManager(activity)
    }

    fun loadMentor(){
        Log.d("check","load mentor")
        db = FirebaseFirestore.getInstance()
        db!!.collection("users").get().addOnSuccessListener {
            for (document in it) {
                if(document.data.get("type")!!.equals("1")){
                    Log.d("check","${document.data}")
                    mentorItemList.add(document.toObject(MentorItem::class.java))
                }
            }
            mentorListFragmentBinding.mentorlistRecycler.adapter?.notifyDataSetChanged()
        }
    }
}