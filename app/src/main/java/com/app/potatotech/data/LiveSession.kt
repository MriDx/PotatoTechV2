package com.app.potatotech.data

class LiveSession {

    companion object {
        private var _instance: LiveSession? = null
        private var _postSession = PostSession(ArrayList<PostData>(), 1)

        fun getInstance(): LiveSession {
            if (_instance == null) {
                _instance = LiveSession()
            }
            return _instance as LiveSession
        }
    }

    //private var postSession = _postSession

    data class PostSession(var list: ArrayList<PostData>, var page: Int)

    @Synchronized
    fun addPosts(newSession: PostSession) {
        _postSession.list.addAll(newSession.list)
        _postSession.page++
        _postSession = PostSession(_postSession.list, _postSession.page)
    }

    fun getPostSession() = _postSession

}