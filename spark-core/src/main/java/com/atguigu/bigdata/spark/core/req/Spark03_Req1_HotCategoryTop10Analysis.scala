package com.atguigu.bigdata.spark.core.req

import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Req1_HotCategoryTop10Analysis {
  def main(args: Array[String]): Unit = {

    // todo: TOP10热门品类
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparkConf)

    // Q1：actionRDD重复使用
    // Q2： cogroup有可能存在shuffle，性能可能较低，


    // 1、读取原始的日志数据，
    val actionRDD = sc.textFile("datas/user_visit_action.txt")

    // 2、将数据转换结构
    val resultRDD = actionRDD.flatMap(action => {
      val datas = action.split("\t")
      if (datas(6) != "-1") {
        List((datas(6), (1, 0, 0)))
      } else if (datas(8) != "\\N") {
        val ids = datas(8).split(",")
        ids.map(id => {
          (id, (0, 1, 0))
        })
      } else if (datas(10) != "\\N") {
        val ids = datas(10).split(",")
        ids.map(id => {
          (id, (0, 1, 0))
        })
      } else {
        Nil
      }
    }).reduceByKey((a1, a2) => {
      (a1._1 + a2._1, a1._2 + a2._3, a1._3 + a2._3)
    })

    val analysisRDD = resultRDD.sortBy(_._2, ascending=false).take(10)   // 元组排序：先比较第一个，如果相同，再比较第二个，再相同比较第三个，以此类推
    println("**************聚合***********************")
    println(analysisRDD.mkString)    // take也是一个采集操作，所以这里不需要collect


    // 6、将结果采集到控制台打印

    sc.stop()

  }

}
