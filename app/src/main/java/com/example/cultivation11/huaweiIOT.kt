package com.example.cultivation11

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class huaweiIOT (device:String,server_id:String,command:String=""){
    //请在下方完善信息
    var HUAWEINAME = "123" //华为账号名
    var IAMINAME = "123" //IAM账户名
    var IAMPASSWORD = "123" //IAM账户密码
    var project_id = "123" //产品ID
    var device_id = device//设备ID
    var service_id = server_id //服务ID
    var commands = command //命令名称
    var token = ""
    init {
        token = gettoken()
    }
    @Throws(Exception::class)
    fun getAtt(att: String, mode: String,num:Int=0): String {
        var strurl = ""
        if (mode === "shadow") strurl =
            "https://iotda.cn-north-4.myhuaweicloud.com" + "/v5/iot/%s/devices/%s/shadow"
        if (mode === "status") strurl =
            "https://iotda.cn-north-4.myhuaweicloud.com" + "/v5/iot/%s/devices/%s"
        strurl = String.format(strurl, project_id, device_id)
        val url = URL(strurl)
        val urlCon = url.openConnection() as HttpURLConnection
        urlCon.addRequestProperty("Content-Type", "application/json")
        urlCon.addRequestProperty("X-Auth-Token", token)
        urlCon.connect()
        val `is` = InputStreamReader(urlCon.inputStream)
        val bufferedReader = BufferedReader(`is`)
        val strBuffer = StringBuffer()
        var line: String? = null
        while (bufferedReader.readLine().also { line = it } != null) {
            strBuffer.append(line)
        }
        `is`.close()
        urlCon.disconnect()
        val result = strBuffer.toString()
        println(result)
        if (mode === "shadow") {
            val objectMapper = ObjectMapper()
            val jsonNode: JsonNode = objectMapper.readValue(result, JsonNode::class.java)
            val tempNode: JsonNode =
                jsonNode.get("shadow").get(num).get("reported").get("properties").get(att)
            val attvaluestr: String = tempNode.asText()
            println("$att=$attvaluestr")
            return attvaluestr
        }
        if (mode === "status") {
            val objectMapper = ObjectMapper()
            val jsonNode: JsonNode = objectMapper.readValue(result, JsonNode::class.java)
            val statusNode: JsonNode = jsonNode.get("status")
            val statusstr: String = statusNode.asText()
            println("status = $statusstr")
            return statusstr
        }
        return "error"
    }
    @Throws(Exception::class)
    fun setCom(com: String, value: String): String {
        var strurl = ""
        strurl = "https://iotda.cn-north-4.myhuaweicloud.com" + "/v5/iot/%s/devices/%s/commands"
        strurl = String.format(strurl, project_id, device_id)
        println(strurl)
        val url = URL(strurl)
        println(url)
        val urlCon = url.openConnection() as HttpURLConnection
        println(urlCon)
        urlCon.addRequestProperty("Content-Type", "application/json")
        urlCon.addRequestProperty("X-Auth-Token", token)
        urlCon.requestMethod = "POST"
        urlCon.doOutput = true
        urlCon.useCaches = false
        urlCon.instanceFollowRedirects = true
        urlCon.connect()
        val body="{\"paras\":{\"$com\":\"$value\"},\"service_id\":\"$service_id\",\"command_name\":\"$commands\"}"
        val writer = BufferedWriter(OutputStreamWriter(urlCon.outputStream, "UTF-8"))
        writer.write(body)
        writer.flush()
        writer.close()
        val `is` = InputStreamReader(urlCon.inputStream)
        val bufferedReader = BufferedReader(`is`)

        val strBuffer = StringBuffer()
        var line: String? = null

        while (bufferedReader.readLine().also { line = it } != null) {
            strBuffer.append(line)
        }
        `is`.close()
        urlCon.disconnect()
        val result = strBuffer.toString()
        println("result"+result)
        return result
    }
    @Throws(Exception::class)
    fun gettoken(): String {
        var strurl = ""
        strurl = "https://iam.cn-north-4.myhuaweicloud.com" + "/v3/auth/tokens?nocatalog=false"
        val tokenstr =
            "{\"auth\": {\"identity\": {\"methods\": [\"password\"],\"password\": {\"user\":{\"domain\": {\"name\": \"$HUAWEINAME\"},\"name\": \"$IAMINAME\",\"password\": \"$IAMPASSWORD\"}}},\"scope\": {\"project\": {\"name\": \"cn-north-4\"}}}}"
        val url = URL(strurl)
        val urlCon = url.openConnection() as HttpURLConnection
        urlCon.addRequestProperty("Content-Type", "application/json;charset=utf8")
        urlCon.doOutput = true
        urlCon.requestMethod = "POST"
        urlCon.useCaches = false
        urlCon.instanceFollowRedirects = true
        urlCon.connect()
        val writer = BufferedWriter(OutputStreamWriter(urlCon.outputStream, "UTF-8"))
        writer.write(tokenstr)
        writer.flush()
        writer.close()
        val headers: Map<*, *> = urlCon.headerFields
        val keys: Set<Any?> = headers.keys
        val token = urlCon.getHeaderField("X-Subject-Token")
        println("X-Subject-Token：$token")
        return token
    }
}