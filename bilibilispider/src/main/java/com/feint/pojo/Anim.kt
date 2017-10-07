package com.feint.pojo

import java.util.*

/**
 * title：番剧标题
 * playCount：播放量
 * episode：集数
 * cover：封面图片地址
 * fans：追番人数
 * date：开播日期
 */
data class Anim(val title:String,
                val playCount:Long,
                val episode:Int,
                val cover: String,
                val fans:Long,
                val date:Date){
    override fun toString(): String {
        return """
            |<tr>
            |<td>$title</td>
            |<td>$playCount</td>
            |<td>$fans</td>
            |<td>$cover</td>
            |<td>$episode</td>
            |</tr>
            """.trimMargin()
    }
}