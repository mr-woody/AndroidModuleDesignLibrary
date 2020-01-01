package com.woody.module1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.okay.router.annotation.Route
import com.woody.commonbusiness.json.GsonUtils
import com.woody.module1.config.ModuleConfig
import com.woody.module1.model.JavaBeanEntry
import com.woody.module1.model.ReportJsonData
import com.woody.module1.model.WrongJsonData

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


        val json8 = "{\"url\":\"https://github.com/xyxyLiu/AndResM\",\"duration\":\"测试\"}"
        val jsonObject8 = GsonUtils.fromJson(json8, JsonObject::class.java)
        val url8 = GsonUtils.getString("url", jsonObject8)
        println("javaBeanEntry8=" + url8)



        val json9 = "{\"url\":{\"name\":\"测试2\"},\"duration\":\"测试\"}"
        val jsonObject9 = GsonUtils.fromJson(json9, JsonObject::class.java)
        val url9 = GsonUtils.getString("url", jsonObject9)
        println("javaBeanEntry9=" + url9)


        val json10 = "{\"url\":[\"name1\",\"name2\",\"name3\"],\"duration\":\"测试\"}"
        val jsonObject10 = GsonUtils.fromJson(json10, JsonObject::class.java)
        val url10 = GsonUtils.getString("url", jsonObject10)
        println("javaBeanEntry10=" + url10)


        val json:String? = null
        Gson().fromJson(json,JavaBeanEntry::class.java)


        val json11 = "{\"list\":[{\"sid\":19,\"name\":\"语文\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlwL7ycV2.png\",\"wrong\":312},{\"sid\":20,\"name\":\"数学\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlxKgGKaI.png\",\"wrong\":167},{\"sid\":21,\"name\":\"英语\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlyZTYOOI.png\",\"wrong\":117},{\"sid\":29,\"name\":\"科学\",\"surl\":\"http://hd.okjiaoyu.cn/hd_QceethAdwY.png\",\"wrong\":14}]}"
        GsonUtils.fromJsonToList(GsonUtils.getString("list",GsonUtils.fromJson(json11,JsonObject::class.java)),JavaBeanEntry::class.java)


        val temp = Gson().fromJson("{\"list\":[{\"showhelp\":false,\"kname\":\"第4节 细胞中的糖类和脂质\",\"degree\":\"0\",\"predegree\":\"0\",\"degreef\":\"0.0\",\"predegreef\":\"0.0\",\"degreepercent\":0,\"predegreepercent\":0,\"list\":[{\"type\":1,\"typename\":\"做练习\",\"recommend\":true,\"lock\":false,\"exercisenum\":null,\"teacherid\":null,\"aliaslist\":null},{\"type\":4,\"typename\":\"做错题\",\"recommend\":false,\"lock\":false,\"exercisenum\":null,\"teacherid\":null,\"aliaslist\":null},{\"type\":3,\"typename\":\"看微课\",\"recommend\":false,\"lock\":false,\"exercisenum\":null,\"teacherid\":null,\"aliaslist\":null},{\"type\":2,\"typename\":\"同步资源\",\"recommend\":true,\"lock\":false,\"exercisenum\":null,\"teacherid\":null,\"aliaslist\":null},{\"type\":5,\"typename\":\"扩展资源\",\"recommend\":false,\"lock\":false,\"exercisenum\":null,\"teacherid\":null,\"aliaslist\":null}],\"kid\":\"11028\",\"ktype\":2,\"klevel\":2,\"kstate\":1,\"isOnDesk\":false,\"magicLearnTag\":0,\"mkid\":\"\",\"mkname\":\"\",\"mlearnstate\":null,\"rlist\":[]}]}", JsonObject::class.java)
        val list = GsonUtils.fromJsonToList(GsonUtils.getString("list",temp), ReportJsonData::class.java)
        if (list == null || list.isEmpty()) {}



        val json13 = "{\"uid\":\"461356\",\"uname\":\"82951428965\",\"name\":\"十二月十九十二\",\"avatarurl\":\"http://hd.okjiaoyu.cn/hd_1emRULWpyNy.jpg\",\"gender\":2,\"grade\":\"三年级\",\"graduation\":0,\"location\":\"宁夏回族自治区 银川市 兴庆区\",\"orglist\":[{\"sid\":\"54\",\"type\":0,\"name\":\"学校\",\"clist\":[]}],\"fontsize\":28,\"status\":2}"
        val jsonObject13 = GsonUtils.fromJsonToMap(json13,String::class.java)
        println("javaBeanEntry11=" + jsonObject13)

        val temp1 = Gson().fromJson("{\"list\":[{\"sid\":1,\"name\":\"语文\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlwL7ycV2.png\",\"wrong\":5},{\"sid\":3,\"name\":\"数学\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlxKgGKaI.png\",\"wrong\":11},{\"sid\":15,\"name\":\"地理\",\"surl\":\"http://hd.okjiaoyu.cn/hd_PPlt6rqQKI.png\",\"wrong\":6}]}", JsonObject::class.java)
        val dataList: List<WrongJsonData>? = GsonUtils.fromJsonToList(GsonUtils.getString("list",temp1), WrongJsonData::class.java)
        if (dataList != null && dataList?.size != 0) {
            println("javaBeanEntry12=" + dataList)
        }
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