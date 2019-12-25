package com.woody.module1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.woody.commonbusiness.json.GsonUtils
import com.woody.module1.config.ModuleConfig
import com.woody.module1.model.JavaBeanEntry

@Route(value =  [ModuleConfig.Module1.URL_MODULE_MAIN_ACTIVITY])
class Module1MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module1_main)


        val json1 ="{\"msg\":\"{\\\"keyId\\\":\\\"8399\\\",\\\"msgId\\\":\\\"6eb3b52c-213a-4da2-88cf-5bb0be7639fb\\\",\\\"msgType\\\":\\\"AUDIO\\\",\\\"payload\\\":\\\"{\\\\\\\"url\\\\\\\":\\\\\\\"https://ap.okjiaoyu.cn/ap_1elrU3j5uP6.m4a\\\\\\\",\\\\\\\"duration\\\\\\\":4.0}\\\",\\\"scene\\\":\\\"deep_learning\\\",\\\"timestamp\\\":1576734184913,\\\"version\\\":1}\",\"msgDate\":1576734184486,\"isread\":0}"
        val javaBeanEntry1 = GsonUtils.fromJson(json1, JavaBeanEntry::class.java)
        println("javaBeanEntry1=" + GsonUtils.toJson(javaBeanEntry1))

        val json2 ="{\"msg\":{\"keyId\":\"8399\",\"msgId\":\"95fc67b3-570e-448d-813f-e98d34dea690\",\"msgType\":\"AUDIO\",\"payload\":{\"url\":\"https://ap.okjiaoyu.cn/ap_1erVuslMZEc.m4a\",\"duration\":4},\"scene\":\"deep_learning\",\"timestamp\":1577071367131,\"version\":1},\"msgDate\":\"1576734184486\"}"
        val javaBeanEntry2 = GsonUtils.fromJson(json2, JavaBeanEntry::class.java)
        println("javaBeanEntry2=" + GsonUtils.toJson(javaBeanEntry2))

        val json3 = "{\"msg\":true,\"msgDate\":\"\",\"duration\":[\"deep\",\"learning\"]}"
        val javaBeanEntry3 = GsonUtils.fromJson(json3, JavaBeanEntry::class.java)
        println("javaBeanEntry3=" + GsonUtils.toJson(javaBeanEntry3))

        val json4 = "{\"msg\":1,\"msgDate\":null,\"duration\":false}"
        val javaBeanEntry4 = GsonUtils.fromJson(json4, JavaBeanEntry::class.java)
        println("javaBeanEntry4=" + GsonUtils.toJson(javaBeanEntry4))

        val json5 = "{\"msg\":null,\"duration\":\"测试\"}"
        val javaBeanEntry5 = GsonUtils.fromJson(json5, JavaBeanEntry::class.java)
        println("javaBeanEntry5=" + GsonUtils.toJson(javaBeanEntry5))

        val json6 = "{\"msg\":\"dadada  \n\r   %$<><>>>>~`+231414   \",\"duration\":\"null\"}"
        val javaBeanEntry6 = GsonUtils.fromJson(json6, JavaBeanEntry::class.java)
        println("javaBeanEntry6=" + GsonUtils.toJson(javaBeanEntry6))


        val json7 = createJsonString(1203,"服务器错误！")
        println("javaBeanEntry7=" + json7)


    }


    /**
     * 对于一些没有response的请求来说，创建一个错误数据模型
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return
     */
    private fun createJsonString(ecode: Int, emsg: String?): String {
        var meta = HashMap<String,Any?>()
        meta["ecode"] = ecode
        meta["emsg"] = emsg

        var json = HashMap<String,HashMap<String,Any?>>()
        json["meta"] = meta
        return GsonUtils.toJson(json)
    }
}