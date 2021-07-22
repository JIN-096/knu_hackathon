package com.example.knuhackthon.navigation.mentorList

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.R
import com.example.knuhackthon.databinding.FragmentMentorListBinding
import com.example.knuhackthon.databinding.FragmentMypageBinding
import com.example.knuhackthon.navigation.board.BoardItem
import com.example.knuhackthon.navigation.mypage.MyPageAdapter

class MentorListFragment : Fragment() {

    companion object{
        fun newInstance() = MentorListFragment()
    }

    val mentorItemList = mutableListOf<MentorItem>()

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
        mentorItemList.add(MentorItem("img1","이정진","경북대학교 컴퓨터학부","총 학생회장 출신"))
        mentorItemList.add(MentorItem("img2","천지완","경북대학교 컴퓨터학부","그냥 졸업생"))
        mentorItemList.add(MentorItem("img3","이준하","경북대학교 컴퓨터학부","연구소 인턴"))
    }
}