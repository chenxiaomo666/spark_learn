package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 每个分区的最大值
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numSlices = 2)

    val mpRDD = rdd.mapPartitions(
      // 迭代器（相当于一个分区的全部数据）
      iter => {
        List(iter.max).iterator

      }
    )

    mpRDD.collect().foreach(println)

    sc.stop()
  }



}
