package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapRDD = rdd.glom()

    // 这里面是两个数组  val mapRDD: RDD[Array[Int]]
    // 将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变

    mapRDD.collect().foreach(x=>{
      x.foreach(println)
    })


    sc.stop()
  }

}
