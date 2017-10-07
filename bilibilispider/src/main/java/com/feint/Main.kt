package com.feint

import com.feint.pojo.Anim
import com.feint.utility.AnimUtils
import com.feint.utility.DocUtils
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import java.io.File
import java.util.*

fun main(args: Array<String>) = runBlocking {
    var id = 0;
    val top50List: MutableList<Anim> = LinkedList()

    val job1=async {

        while (id < 7000) {
            val doc = DocUtils("https://bangumi.bilibili.com/anime").getDoc(id++)
            if (doc.html().isEmpty()) continue
            else if (doc.select(".info-title").text() == "") continue

            val anim = Anim(title = doc.select(".info-title").text(),
                    playCount = AnimUtils.toPlayCount(doc.select(".info-count-item-play em").text()),
                    episode = AnimUtils.toEpisode(doc.select(".info-update span")[1].text()),
                    cover = doc.select(".bangumi-preview img").attr("src"),
                    fans = AnimUtils.toPlayCount(doc.select(".info-count-item-fans em").text()),
                    date = AnimUtils.toDate(doc.select(".info-update span")[0].text())
            )
            println("$id: ${anim.title}")

            synchronized(top50List) {
                top50List.add(anim)
                if (top50List.size == 51) {

                    top50List.sortByDescending {
                        it.playCount
                    }
                    top50List.removeAt(50)
                }
            }
        }

    }

    val job2=async {

        val file = File("/Users/feint/bilibili.html")
        if (!file.exists()) file.createNewFile()
        while (id < 7000) {

            if (top50List.size >= 50) {
                var outStr = ""
                synchronized(top50List) {
                    top50List.forEach {
                        outStr += it.toString()
                    }
                }
                file.outputStream().write("""
                    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"/>
                    <table class="table table-bordered table-striped">
                    <thead>
                    <tr><th>标题</th><th>播放量</th><th>追番</th><th>封面地址</th><th>集数</th></tr>
                    </thead>
                    <tbody>
                    $outStr
                    </tbody>
                    </table>
                    """.toByteArray())
            }

            println("---")

            delay(5000)
        }

    }

    job1.join()
    job2.join()

    top50List.forEach {
        println(it.toString())
    }
}


