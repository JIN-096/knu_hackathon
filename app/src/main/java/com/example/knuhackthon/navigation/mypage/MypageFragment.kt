package com.example.knuhackthon.navigation.mypage

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knuhackthon.DividerItemDecoration
import com.example.knuhackthon.R
import com.example.knuhackthon.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    var menulist = mutableListOf<MyPageMenu>()
    private lateinit var mypageFragmentBinding: FragmentMypageBinding
    var db : FirebaseFirestore? = null
    var auth : FirebaseAuth? = null
    var uid : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mypageFragmentBinding = FragmentMypageBinding.inflate(inflater, container, false)
        return mypageFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMenu()
        var adapter = MyPageAdapter()
        adapter.menulist = menulist

        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        mypageFragmentBinding.mypageRecycler.addItemDecoration(decoration)


        mypageFragmentBinding.mypageRecycler.adapter = adapter
        mypageFragmentBinding.mypageRecycler.layoutManager = LinearLayoutManager(activity)

    }

    fun loadMenu(){
        val res_list = listOf(R.drawable.settings)
        val menuname = listOf("멘토 멘티 상태 변경","로그아웃")

        menuname.forEach { name ->
            menulist.add(MyPageMenu(res_list.get(0), name))
        }
    }
}