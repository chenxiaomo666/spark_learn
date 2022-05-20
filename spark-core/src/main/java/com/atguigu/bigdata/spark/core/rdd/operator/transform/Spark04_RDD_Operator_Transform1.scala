package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(
      "hello spark",
      "hello python",
      "hello scala"
    ))
    val mapRDD = rdd.flatMap(
      x => {
        x.split(" ")
      }
    )

    mapRDD.collect().foreach(println)





    sc.stop()
  }

}
