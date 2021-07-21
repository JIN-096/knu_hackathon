package com.example.knuhackthon.navigation.chattingList

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.R
import com.example.knuhackthon.databinding.FragmentChattingListBinding
import com.example.knuhackthon.databinding.FragmentMentorListBinding
import com.example.knuhackthon.navigation.mentorList.MentorAdapter
import com.example.knuhackthon.navigation.mentorList.MentorItem
import com.example.knuhackthon.navigation.mentorList.MentorListFragment

class ChattingListFragment : Fragment() {

    companion object{
        fun newInstance() = ChattingListFragment()
    }

    val chattingItemList = mutableListOf<ChattingItem>()

    private lateinit var chattingListFragmentBinding : FragmentChattingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chattingListFragmentBinding = FragmentChattingListBinding.inflate(inflater,container,false)
        return chattingListFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadChatting()
        var adapter = ChattingAdapter()
        adapter.chattingItemList = chattingItemList

        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        chattingListFragmentBinding.chattinglistRecycler.addItemDecoration(decoration)


        chattingListFragmentBinding.chattinglistRecycler.adapter = adapter
        chattingListFragmentBinding.chattinglistRecycler.layoutManager = LinearLayoutManager(activity)
    }

    fun loadChatting(){
        chattingItemList.add(ChattingItem("학생1","도와주세요"))
        chattingItemList.add(ChattingItem("학생2","캐리좀"))
    }


}