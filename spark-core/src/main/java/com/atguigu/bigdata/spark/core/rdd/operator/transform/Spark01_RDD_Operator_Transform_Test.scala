package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Operator_Transform_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 获取文件所有url信息
    val rdd = sc.textFile("datas/apache.log")

    val mapRDD = rdd.map(
      line => {
        line.split(" ")(6)
      }
    )

    mapRDD.collect().foreach(println)



    sc.stop()
  }

  def mapFunction(num: Int): Int = {
    num*2
  }

}
