package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark10_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // coalesce
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 4)

    // coalesce默认情况下不会将数据打乱重新组合的，可能会导致数据倾斜，分区数据不均衡
    // 可以使用shffle将数据打乱重新组合，但是没有规律，
    val coalesceRDD = rdd.coalesce(2, true)
    // 缩减分区，从四个变成两个，
    // 但是如果分区数量比之前的大，这是没有效果的，
    // 如果想扩大分区，需将shuffle赋值为true，这样可以将数据打乱，就可以重新分区，扩大分区了，

    coalesceRDD.saveAsTextFile("output")
    sc.stop()
  }

}
