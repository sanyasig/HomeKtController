package com.nani.homecontroller.services

import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPut
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.util.*

class LifxService : HomeService {

    var accessToken = "ca2e9f698f11a24b494a7e88c16a7cd3432c9545348caa585510139448b7718a"

    override fun doAction(label: String, message: String): ServiceResponse {

        var action = "off"

        if (message.equals("turnon", ignoreCase = true)) action = "on"

        var put = HttpPut("https://api.lifx.com/v1/lights/all/state")
        var postParameters = ArrayList<BasicNameValuePair>()
        postParameters.add(BasicNameValuePair("power", action))

        put.setEntity(UrlEncodedFormEntity(postParameters))

        put.addHeader("Authorization", "Bearer "+accessToken)
        val httpClient = DefaultHttpClient()
        val response = httpClient.execute(put)
        val entity = response.entity
        val responseString = EntityUtils.toString(entity, "UTF-8")
        var json = JSONObject(responseString)

        return ServiceResponse("tets", "test")
    }

}
