package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat

object Spark06_RDD_Operator_Transform2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // groupBy
    // 83.149.9.216 - - 17/05/2015:10:05:43 +0000 GET /presentations/logstash-monitorama-2013/images/kibana-dashboard3.png
    val rdd = sc.textFile("datas/apache.log")
//    val mapRDD = rdd.map(x => x.split(" ")(3)).groupBy(x => x.slice(11, 13))
//
//    mapRDD.collect().foreach(println)

    val timeRDD = rdd.map(
      line => {
        val dates = line.split(" ")
        val time = dates(3)

        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date = sdf.parse(time)
        val sdf1 = new SimpleDateFormat("HH")
        val hour = sdf1.format(date)
        (hour, 1)
      }
    ).groupBy(x => x._1)

    timeRDD.map({
      case(hour, iter)=>{
        (hour, iter.size)
      }
    }).sortBy(x=>x._2).collect().foreach(println)

    sc.stop()
  }

}
