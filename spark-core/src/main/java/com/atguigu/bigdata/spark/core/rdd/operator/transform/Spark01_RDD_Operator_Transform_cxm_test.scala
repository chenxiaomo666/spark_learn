package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object Spark01_RDD_Operator_Transform_cxm_test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 找到RDD中的最大值
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    var double_list = new ListBuffer[Int]()
    double_list.append(1)
    val result = rdd.map(num=>{
      println("，<<<<<<<<<<<")
      double_list.append(num*5)
      num
    })

    result.collect().foreach(println)


    println(double_list.toString())


    sc.stop()
  }

  def mapFunction(num: Int): Int = {
    num*2
  }

}
