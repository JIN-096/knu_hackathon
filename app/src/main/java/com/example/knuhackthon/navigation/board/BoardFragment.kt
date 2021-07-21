package com.example.knuhackthon.navigation.board

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.databinding.FragmentBoardBinding


class BoardFragment : Fragment() {

    companion object{
        fun newInstance() = BoardFragment()
    }

    private lateinit var boardFragmentBinding : FragmentBoardBinding
    val boardItemList = mutableListOf<BoardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        boardFragmentBinding = FragmentBoardBinding.inflate(inflater,container,false)
        return boardFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val boardAdapter = BoardAdapter()
        loadData()
        boardAdapter.boardItemList = boardItemList

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        boardFragmentBinding.boardRecycler.addItemDecoration(decoration)

        boardFragmentBinding.boardRecycler.adapter = boardAdapter
        boardFragmentBinding.boardRecycler.layoutManager = LinearLayoutManager(activity)
    }

    fun loadData(){

    }
}