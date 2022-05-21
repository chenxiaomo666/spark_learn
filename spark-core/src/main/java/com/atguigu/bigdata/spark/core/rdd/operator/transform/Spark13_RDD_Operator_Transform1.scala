package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark13_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // zip 必须分区数量一致，rdd大小一致，分区内的数据保持一致
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 3)
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6), 2)

    val rdd3 = rdd1.zip(rdd2)
    println(rdd3.collect().mkString(", "))


    sc.stop()
  }

}
