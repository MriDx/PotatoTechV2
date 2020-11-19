package com.app.potatotech.ui.fragment.home_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.potatotech.apis.ApiHandler
import com.app.potatotech.apis.JSONHelper
import com.app.potatotech.apis.callback.RequestType
import com.app.potatotech.data.LiveSession
import com.app.potatotech.data.PostData
import com.app.potatotech.utils.Utils
import com.dev.aii.api.callback.ApiResponseListener
import org.json.JSONArray
import org.json.JSONObject

class HomeFragmentViewModel : ViewModel(), ApiResponseListener {

    private var _posts = MutableLiveData<ArrayList<PostData>>()
    val posts: LiveData<ArrayList<PostData>> = _posts

    private var _page = MutableLiveData<Utils.Page>()
    val page: LiveData<Utils.Page> = _page


    fun load(more: Boolean) {
        if (!more)
            if (LiveSession.getInstance().getPostSession().list.size > 0) {
                _posts.postValue(LiveSession.getInstance().getPostSession().list)
                return
            }
        ApiHandler.instance.apply {
            this.apiResponseListener = this@HomeFragmentViewModel
            this.loadPosts(LiveSession.getInstance().getPostSession().page)
        }

    }

    override fun onResponse(
        success: Boolean,
        reqType: RequestType,
        responseObj: JSONObject?,
        responseArr: JSONArray?
    ) {
        if (!success) {
            Log.d("mridx", "onResponse: request failed")
            return
        }
        when (reqType) {
            RequestType.POSTS -> {
                /* _posts.postValue(
                     responseArr?.let { JSONHelper.parsePosts(it) }
                 )*/
                responseArr?.let { it ->
                    JSONHelper.parsePosts(it).also { list ->
                        LiveSession.getInstance().addPosts(
                            LiveSession.PostSession(
                                list,
                                0
                            )
                        )
                        _posts.postValue(list)
                    }
                }
            }
        }
    }


}