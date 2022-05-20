package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_Operator_Transform2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(List(1, 2),3, List(4, 5)))

    // 模式匹配，如果是List就直接返回，如果是Int类型，就变成集合返回
    val flatRdd = rdd.flatMap(
      data => {
        data match {
          case list: List[_] => list
          case dathhh => List(dathhh)
        }
      }
    )

    flatRdd.collect().foreach(println)

    sc.stop()
  }

}
