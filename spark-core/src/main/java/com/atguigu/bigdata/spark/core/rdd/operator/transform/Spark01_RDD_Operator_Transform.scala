package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // RDD每个元素乘以2
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
//    val result = rdd.map((num: Int)=>{
//      num*2
//    })
// map将处理的数据逐条进行转换，相当于for循环，进入循环里面再逐步进行转化，
    val result = rdd.map(num=>num*2)

    result.collect()
    println(result.foreach(println))


    sc.stop()
  }

  def mapFunction(num: Int): Int = {
    num*2
  }

}
