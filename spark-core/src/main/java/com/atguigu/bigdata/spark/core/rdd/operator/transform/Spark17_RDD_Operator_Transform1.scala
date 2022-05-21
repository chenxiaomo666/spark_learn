package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark17_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // 存在函数的柯里化，有两个参数列表
    // 第一个参数列表需要传递一个参数，表示为初始值，主要用于碰见第一个key的时候，和value进行分区内的计算
    // 第二个参数列表有两个参数：第一个表示分区内计算规则，第二个表示分区间计算规则
    val aggRDD = rdd.aggregateByKey(0)(   // 给个0很不合理哎，是为了和初始比较，因为第一个值没办法比较大小，但是，
      (x, y) => math.max(x, y), // 第一个表示分区内取最大值
      (x, y) => x + y // 第二个参数是分区间最大值相加
    )

    aggRDD.collect().foreach(println)
//    (b,3)
//    (a,5)


    sc.stop()
  }

}
