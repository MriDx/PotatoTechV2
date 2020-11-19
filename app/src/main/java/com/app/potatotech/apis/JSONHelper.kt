package com.app.potatotech.apis

import com.app.potatotech.data.PostData
import org.json.JSONArray
import org.json.JSONObject

class JSONHelper {

    companion object {

        fun parsePost(response: JSONObject): PostData {
            val id = response.getInt("id")
            val date = response.getString("date")
            val link = response.getString("link")
            val title = response.getJSONObject("title").getString("rendered")
            val content = response.getJSONObject("content").getString("rendered")
            val author = response.getJSONObject("_embedded").getJSONArray("author").getJSONObject(0)
                .getString("name")
            val images = response.getJSONObject("_embedded").getJSONArray("wp:featuredmedia")
                .getJSONObject(0).getJSONObject("media_details").getJSONObject("sizes")
            val imgL = images.getJSONObject("full").getString("source_url")
            //val imgML = images.getJSONObject("medium_large").getString("source_url")
            val imgM = images.getJSONObject("medium").getString("source_url")
            val imgT = images.getJSONObject("thumbnail").getString("source_url")
            val list = ArrayList<String>()
            list.add(imgL)
            //list.add(imgML)
            list.add(imgM)
            list.add(imgT)
            return PostData(id, date, link, title, content, author, list, false)
        }

        fun parsePosts(response: JSONArray): ArrayList<PostData> {
            val list = ArrayList<PostData>()
            for (i in 0 until response.length()) {
                list.add(parsePost(response.getJSONObject(i)))
            }
            return list
        }

    }

}