package com.example.knuhackthon.navigation.board

data class BoardItem(
    val author: String? = null,
    val title: String? = null,
    val content: String? = null,
    val date: String? = null,
    //val CommentCnt: Int = 0,
    var uid: String? = null,
    val bid: Int = 0,
    var favorites: MutableMap<String,Boolean> = HashMap()){
    data class Comment(var uid : String? = null,
                       var name : String? = null,
                       var comment : String? = null,
                       var timestamp : Long? = null)}
