package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat

object Spark07_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // filter
    val rdd = sc.textFile("datas/apache.log")
//    83.149.9.216 - - 17/05/2015:10:05:43 +0000 GET /presentations/logstash-monitorama-2013/images/kibana-dashboard3.png

    val filterRDD = rdd.filter(
      line => {
        val date = line.split(" ")(3)
        date.startsWith("17/05/2015")   // 字符串操作
//        val dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss")
//        val time = dateFormat.parse(date)
//        val dateFormat2 = new SimpleDateFormat("dd/MM/yyyy")
//        val dayTime = dateFormat2.format(time)
//        val targrtTime = dateFormat2.parse("17/05/2015")
//        dayTime == dateFormat2.format(targrtTime)
      }
    ).map(
      line => {
        val lineList = line.split(" ")
        (lineList(3), lineList(6))
      }
    )
    filterRDD.collect().foreach(println)

    sc.stop()
  }

}
