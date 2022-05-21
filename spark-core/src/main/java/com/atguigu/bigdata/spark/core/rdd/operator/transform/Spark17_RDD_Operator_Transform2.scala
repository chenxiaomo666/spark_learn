package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark17_RDD_Operator_Transform2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)


//    val aggRDD = rdd.aggregateByKey(0)(
//      (x, y) => x + y,
//      (x, y) => x + y
//    )

    // 分区内和分区间的计算逻辑一样
    val aggRDD = rdd.foldByKey(0)(
      (x, y) => x + y
    )

    aggRDD.collect().foreach(println)



    sc.stop()
  }

}
