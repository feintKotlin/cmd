package com.feint.utility

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class DocUtils(val baseUrl:String) {
    fun getDoc(id:Int)=try {
        val conn = Jsoup.connect("$baseUrl/$id")
        val chromeConn=conn.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
        chromeConn.get()
    }catch (e:Exception){
        Document("")
    }
}