package com.example.cultivation11

import com.huaweicloud.sdk.core.auth.AbstractCredentials
import com.huaweicloud.sdk.core.auth.BasicCredentials
import com.huaweicloud.sdk.core.auth.ICredential
import com.huaweicloud.sdk.core.exception.ConnectionException
import com.huaweicloud.sdk.core.exception.RequestTimeoutException
import com.huaweicloud.sdk.core.exception.ServiceResponseException
import com.huaweicloud.sdk.iotda.v5.IoTDAClient
import com.huaweicloud.sdk.iotda.v5.model.ChangeRuleStatusRequest
import com.huaweicloud.sdk.iotda.v5.model.RuleStatus
import com.huaweicloud.sdk.iotda.v5.model.ShowRuleRequest
import com.huaweicloud.sdk.iotda.v5.region.IoTDARegion


var IAMPASSWORD = "aa12345678" //IAM账户密码
var wendu:String=""
var shidu=""
var CO2=""
var NH3=""
var siliao=""
var yinyongshui=""
val starArray = arrayOf("请选择","温度", "湿度", "二氧化碳","氨气","饲料","饮用水")
var state_=""
var line=""
var wendu_group= arrayListOf<Float>()
var shidu_group=arrayListOf<Float>()
var CO2_group=arrayListOf<Float>()
var NH3_group=arrayListOf<Float>()
var siliao_group=arrayListOf<Float>()
var yinyongshui_group=arrayListOf<Float>()
var n=0

//更改设备联动状态
fun ChangeRuleStatusSolution(ruleid:String,status:String) {
    val ak = "123"
    val sk = ""
    val auth: ICredential = BasicCredentials()
        .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE) // Used in derivative ak/sk authentication scenarios
        .withAk(ak)
        .withSk(sk)
    val client = IoTDAClient.newBuilder()
        .withCredential(auth)
        .withRegion(IoTDARegion.valueOf("cn-north-4"))
        .build()
    val request = ChangeRuleStatusRequest()
    request.withRuleId(ruleid)
    val body = RuleStatus()
    body.withStatus(status)
    request.withBody(body)
    try {
        val response = client.changeRuleStatus(request)
        println(response.toString())
    } catch (e: ConnectionException) {
        e.printStackTrace()
    } catch (e: RequestTimeoutException) {
        e.printStackTrace()
    } catch (e: ServiceResponseException) {
        e.printStackTrace()
        println(e.httpStatusCode)
        println(e.requestId)
        println(e.errorCode)
        println(e.errorMsg)
    }
}
//显示设备联动状态
fun ShowRuleSolution() {
    val ak = "123"
    val sk = ""
    val auth: ICredential = BasicCredentials()
        .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE) // Used in derivative ak/sk authentication scenarios
        .withAk(ak)
        .withSk(sk)
    val client = IoTDAClient.newBuilder()
        .withCredential(auth)
        .withRegion(IoTDARegion.valueOf("cn-north-4"))
        .build()
    val request = ShowRuleRequest()
    request.withRuleId("123")
    try {
        val response = client.showRule(request).status
        state_=response
    } catch (e: ConnectionException) {
        e.printStackTrace()
    } catch (e: RequestTimeoutException) {
        e.printStackTrace()
    } catch (e: ServiceResponseException) {
        e.printStackTrace()
        println(e.httpStatusCode)
        println(e.requestId)
        println(e.errorCode)
        println(e.errorMsg)
    }
}




