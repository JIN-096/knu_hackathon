package com.example.knuhackthon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.knuhackthon.databinding.ActivityMainBinding
import com.example.knuhackthon.navigation.board.BoardFragment
import com.example.knuhackthon.navigation.mentorList.MentorListFragment
import com.example.knuhackthon.navigation.mypage.MypageFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var mentorListFragment: MentorListFragment
    private lateinit var mypageFragment: MypageFragment
    private lateinit var boardFragment: BoardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
        binding.bottomNavigation.apply {
            setOnItemSelectedListener { // 메뉴 고를 때
                when(it.itemId){
                    R.id.action_board -> {
                        binding.mainToolbarTextview.text = "게시판"
                        boardFragment = BoardFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,boardFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_mentorlist -> {
                        binding.mainToolbarTextview.text = "멘토 리스트"
                        mentorListFragment = MentorListFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,mentorListFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_mypage -> {
                        binding.mainToolbarTextview.text = "마이 페이지"
                        mypageFragment = MypageFragment.newInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content,mypageFragment).commit()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
            selectedItemId = R.id.action_mentorlist // 초기 화면
        }
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_item, menu)
        return true
    }*/

}