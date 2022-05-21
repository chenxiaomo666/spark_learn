package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark09_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // distinct
    val rdd = sc.makeRDD(List(1, 2, 3, 3, 5, 6, 2, 8, 5, 10), 2)

    val distinctRDD = rdd.distinct()   //

    distinctRDD.collect().foreach(println)

    val disList = List(11,22,22,11).distinct     // 利用hashmap去重
    println(disList.mkString(", "))


    sc.stop()
  }

}
