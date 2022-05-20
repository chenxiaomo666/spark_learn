package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.commons.collections.ComparatorUtils
import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    val glomRDD = rdd.glom()

    val maxRDD = glomRDD.map(
      array => array.max
    )
    println(maxRDD.collect().sum)

    // cxm for test
//    var result = 0
//    glomRDD.collect().foreach(x=>{
//      var cur_max=0
//      x.foreach(
//        y => {
//          if(y>cur_max) cur_max=y
//        }
//      )
//      result += cur_max
//      println(s"cur_max: ${cur_max}")
//    })
//
//    println(result)


    sc.stop()
  }

}
