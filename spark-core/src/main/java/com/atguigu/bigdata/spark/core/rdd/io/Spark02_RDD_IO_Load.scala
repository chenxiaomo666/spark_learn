package com.atguigu.bigdata.spark.core.rdd.io

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_IO_Load {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.textFile("output1")
    rdd1.collect().foreach(println))
    val rdd2 = sc.objectFile[(String, Int)]("output2")
    rdd2.collect().foreach(println)
    val rdd3 = sc.sequenceFile[String, Int]("output3")
    rdd3.collect().foreach(println)


    // 关闭连接
    sc.stop()
  }

}
