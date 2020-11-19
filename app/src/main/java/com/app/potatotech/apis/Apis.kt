package com.app.potatotech.apis

class Apis {

    companion object{
        private const val base = "https://potatotech.in/wp-json/wp/v2/"
        const val posts = "${base}posts?&_embed&per_page=10&page="
    }

}