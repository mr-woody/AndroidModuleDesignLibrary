package com.woody.module1.model

import java.io.Serializable

/**
 * @author wzj
 * 推荐的辅导老师
 */
class Referee : Serializable {
    /**
     * 头像地址链接
     */
    var icon: String? = null
    /**
     * 推荐人id
     */
    var id: String? = null
    /**
     * 名称或昵称
     */
    var name: String? = null
    /**
     * 学校/机构/老师发布
     */
    var school: String? = null

    override fun toString(): String {
        return "Referee{" +
                "icon='" + icon + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", school='" + school + '\''.toString() +
                '}'.toString()
    }
}
