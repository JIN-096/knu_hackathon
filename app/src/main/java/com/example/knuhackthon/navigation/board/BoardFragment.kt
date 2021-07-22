package com.example.knuhackthon.navigation.board

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.BoardAdd
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.databinding.FragmentBoardBinding
import com.example.knuhackthon.navigation.mentorList.MentorItem
import com.google.firebase.firestore.FirebaseFirestore


class BoardFragment : Fragment() {



    private lateinit var boardFragmentBinding : FragmentBoardBinding
    val boardItemList = mutableListOf<BoardItem>()
    var db : FirebaseFirestore? = null

    companion object{
        fun newInstance() = BoardFragment()
        //val boardItemList = mutableListOf<BoardItem>()
    }

    fun listAdd(boardItem: BoardItem){
        boardItemList.add(boardItem)
        boardFragmentBinding.boardRecycler.adapter?.notifyDataSetChanged()

    }
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
        boardAdapter.boardItemList = boardItemList

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        boardFragmentBinding.boardRecycler.addItemDecoration(decoration)
        boardFragmentBinding.boardBtnAdd.setOnClickListener {
            val intent = Intent(it.context, BoardAdd::class.java)
            intent.putExtra("bid", boardItemList.size + 1)

            startActivity(intent)

        }
        boardFragmentBinding.boardRecycler.adapter = boardAdapter
        boardFragmentBinding.boardRecycler.layoutManager = LinearLayoutManager(activity)

    }

    override fun onResume() {
        super.onResume()
        loadData()
        Log.d("check", "onResume: "+boardItemList.size)

    }

    fun loadData(){
        db = FirebaseFirestore.getInstance()
        db!!.collection("contents").get().addOnSuccessListener {
            boardItemList.clear()
            for (document in it) {
                    Log.d("check","${document.data}")
                    boardItemList.add(document.toObject(BoardItem::class.java))

            }
            boardFragmentBinding.boardRecycler.adapter?.notifyDataSetChanged()
        }

//        boardItemList.add(BoardItem("지완","배고파요","저녁 메뉴 추천좀요","2021-07-12 18:21","0",1,1))
//        boardItemList.add(BoardItem("지혜","키아누","커피 요즘 너무 맛있어진듯","2021-07-12 13:21","1",2,2))
//        boardItemList.add(BoardItem("성기","줄임표시확인줄임표시확인줄임표시확인줄임표시확인","줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인","2021-07-12 18:21","3",3,3))
//        boardItemList.add(BoardItem("성빈","까만 안경","사랑해요 나도~ 울고 있어요~ 오 난~~ 보고 싶어서 만나고 싶어서 죽고만 싶어요~","2021-07-12 11:21","99",4,4))
    }
}