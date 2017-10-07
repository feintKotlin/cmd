package com.feint.utility

import java.util.*

object AnimUtils {
    fun toDate(dateStr: String): Date {
        return Date()
    }

    fun toEpisode(eStr: String): Int = if (eStr.contains("连载中")) {
        -1
    } else if (eStr.contains("即将开播")) {
        0
    } else {
        try {
            eStr.replace("已完结, 全", "").replace("话", "").toInt()
        } catch (e: Exception) {
            -1
        }
    }


    fun toPlayCount(pcStr: String): Long = if (pcStr.contains("万")) {
        (pcStr.replace("万", "").toDouble() * 1000_0L).toLong()
    } else if (pcStr.contains("亿")) {
        (pcStr.replace("亿", "").toDouble() * 100_000_000L).toLong()
    } else if (pcStr == "-") {
        0L
    } else
        pcStr.toLong()

}