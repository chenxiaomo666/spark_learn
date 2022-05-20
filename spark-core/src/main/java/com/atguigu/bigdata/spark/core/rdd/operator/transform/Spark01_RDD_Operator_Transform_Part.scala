package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Operator_Transform_Part {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // map操作后不改变数的分区
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numSlices = 2)
    val mapRDD = rdd.map(_*2)


    mapRDD.collect()

    rdd.saveAsTextFile("output")
    mapRDD.saveAsTextFile("output1")


    sc.stop()
  }


}
