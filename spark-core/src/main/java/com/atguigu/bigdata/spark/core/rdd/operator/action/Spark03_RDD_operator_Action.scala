package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark03_RDD_operator_Action {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // aggregate，第一个是一个初始值，第二个参数列表分别是分区内的计算规则，和分区间的计算规则，
    // 初始值，不仅仅参与分区内计算，也会参加分区间计算，
    // aggregateByKkey:初始值只会参加分区内计算
    val result  = rdd.aggregate(0)(_+_, _+_)
    println(result)

    // 当分区内和分区间的运行规则相同的时候，可以使用fold代替aggregate（大部分情况下应该都是相同的运算规则）
    // 初始值也会参与分区间计算
    val foldResult = rdd.fold(0)(_+_)
    println(foldResult)


    sc.stop()

  }
}
