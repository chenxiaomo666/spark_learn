package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_operator_Action {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // reduce会两两聚合，先聚合分区内的，再聚合分区间的，直接出结果，因为它是行动算子
    // 注意这里并不会改变rdd的什么东西，
    // val result = rdd.reduce((x, y) => x + y)
    val result = rdd.reduce(_+_)

    println(result)

    // collect : Array[Int]：会将不分区的数据按照分区内的顺序采集到Driver端内存中，形成数组
    val ints = rdd.collect()
    println(ints.mkString(", "))


    // count: 统计数据源中的数据个数
    val cnt = rdd.count()
    println(cnt)

    // first: 获取数据源中的第一个
    val first = rdd.first()
    println(first)

    // take:取多少个，返回的是数组，同collect
    val take = rdd.take(3)
    println(take.mkString(", "))

    // takeOrdered: 取数据排序后的n个数据，
    val rdd2 = sc.makeRDD(List(4, 2, 51, 6))
    val takeOrdered = rdd2.takeOrdered(3)
    println(takeOrdered.mkString(", "))


    sc.stop()

  }
}
