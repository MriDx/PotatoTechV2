package com.app.potatotech.apis

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.app.potatotech.utils.VolleySingleton
import com.dev.aii.api.callback.ApiResponseListener
import com.app.potatotech.apis.callback.RequestType
import org.json.JSONException
import org.json.JSONObject

class ApiHandler {

    var apiResponseListener: ApiResponseListener? = null

    private val defaultRetryPolicy = DefaultRetryPolicy(
        0,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    companion object {
        var instance = ApiHandler()
    }

    private fun parseError(it: VolleyError?): JSONObject? {
        if (it!!.networkResponse == null) return null
        try {
            return JSONObject(String(it.networkResponse.data))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadPosts(page: Int) {
        val arrayRequest = JsonArrayRequest(Request.Method.GET, "${Apis.posts}$page", null,
            { apiResponseListener?.onResponse(true, RequestType.POSTS, null, it) },
            {
                apiResponseListener?.onResponse(
                    false,
                    RequestType.POSTS,
                    parseError(it),
                    null
                )
            }).also {
            it.retryPolicy = defaultRetryPolicy
        }
        VolleySingleton.instance?.addToRequestQueue(arrayRequest)
    }

}