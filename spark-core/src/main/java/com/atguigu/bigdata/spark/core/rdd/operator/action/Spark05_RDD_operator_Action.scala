package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_operator_Action {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4, 2, 3))
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 1),("c", 1),("a", 1)
    ))

    // countByValue: 统计rdd里面的不同的value有多少个
    rdd.saveAsTextFile("outPut")
    rdd.saveAsObjectFile("outPut1")
    rdd1.saveAsSequenceFile("outPut2") // 这里要求rdd是K-V类型的

    sc.stop()

  }
}
