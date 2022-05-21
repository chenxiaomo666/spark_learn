package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark13_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 双value操作   数据类型需要保持一致
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))

    // 交集
    rdd1.intersection(rdd2).collect().foreach(println)

    // 并集
    println("***************************************")
    rdd1.union(rdd2).collect().foreach(println)   // 这里会是八个元素，因为是List，不会对重复元素进行去重

    // 差集
    println("***************************************")
    rdd1.subtract(rdd2).collect().foreach(println)   // 去掉重合的

    // 拉链   可以不要求两个RDD数据类型一致
    println("***************************************")
    rdd1.zip(rdd2).collect().foreach(println)
//    (1,3)
//    (2,4)
//    (3,5)
//    (4,6)

    sc.stop()
  }

}
