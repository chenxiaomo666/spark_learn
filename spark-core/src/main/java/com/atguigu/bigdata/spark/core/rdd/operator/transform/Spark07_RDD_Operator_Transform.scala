package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark07_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // filter
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    // def filter(f:T=>Boolean):RDD[T]
    val filterRDD = rdd.filter(x => x % 2 != 0)

    filterRDD.collect().foreach(println)

    sc.stop()
  }

}
