package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark12_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // sortBy
    // 排序之后，分区数量不会变，但是会shuffle操作，将数据打乱，毕竟是排序
    val rdd = sc.makeRDD(List(
      ("a", 3), ("b", 5), ("c", 2), ("d", 2)
    ), 2)
    val sortRDD = rdd.sortBy(x => x._1, ascending = false)  // 降序  ascending意思是提升

    sortRDD.collect().foreach(println)

    sc.stop()
  }

}
