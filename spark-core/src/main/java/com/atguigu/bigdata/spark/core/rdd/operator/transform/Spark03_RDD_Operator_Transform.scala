package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark03_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapRDD = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        if (index == 1) {  // index: 0,1
          iter
        } else {
//          Nil.iterator  // 代表是一个空迭代器
          "一个".iterator
        }
      }
    )

    mapRDD.collect().foreach(println)

    sc.stop()
  }

}
