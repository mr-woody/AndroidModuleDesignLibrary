package com.woody.module1.model

import java.io.Serializable

/**
 * @author wzj
 * 关联的别名数据
 */
class AliasInfo : Serializable {
    /**
     * 指派时间
     */
    var assigntime: String? = null
    /**
     * 知识点id
     */
    var kid: String? = null
    /**
     * 知识点所在级别（1、2、3）
     */
    var klevel: Int = 0
    /**
     * 知识点名称
     */
    var kname: String? = null
    /**
     * 知识点类型： 1 章节体系知识点 2 知识点体系知识点 3：校本体系知识点
     */
    var ktype: Int = 0
    /**
     * 最后聊天时间
     */
    var lmtime: String? = null
    /**
     * 推荐人
     */
    var referee: Referee? = null
    /**
     * 1正在服务2服务完结
     */
    var servicestate: Int = 0
    /**
     * 1占三个格
     * 2占一个格
     */
    var type: Int = 0
    /**
     * 话题id
     */
    var topicid: String? = null
    /**
     * 未读消息数
     */
    var unReadMsgNum : Int = 0
    override fun toString(): String {
        return "AliasInfo(assigntime=$assigntime, kid=$kid, klevel=$klevel, kname=$kname, ktype=$ktype, lmtime=$lmtime, referee=$referee, servicestate=$servicestate, type=$type, topicid=$topicid, unReadMsgNum=$unReadMsgNum)"
    }


}
