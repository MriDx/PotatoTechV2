package com.dev.aii.api.callback

import com.app.potatotech.apis.callback.RequestType
import org.json.JSONArray
import org.json.JSONObject

interface ApiResponseListener {


    fun onResponse(success: Boolean, reqType : RequestType, responseObj: JSONObject?, responseArr: JSONArray?)

}