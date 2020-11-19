package com.app.potatotech.data

import java.io.Serializable

data class PostData(
    var id: Int,
    var date: String,
    var link: String,
    var title: String,
    var content: String,
    var author: String,
    var imageList: ArrayList<String>,
    var loved: Boolean
) : Serializable