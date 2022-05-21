package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark11_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // coalesce可以将shuffle置为true，扩大分区，
    // 但是有个专门的扩大分区函数reparition
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)


    val coalesceRDD = rdd.repartition(3)
    coalesceRDD.saveAsTextFile("output")
    sc.stop()
  }

}
