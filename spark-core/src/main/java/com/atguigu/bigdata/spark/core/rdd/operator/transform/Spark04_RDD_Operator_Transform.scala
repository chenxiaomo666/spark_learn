package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd = sc.makeRDD(List(List(1, 2), List(4, 5)))
    val mapRDD = rdd.flatMap(           // 本来rdd是两个list元素，经过flatMap后，里面的LIst都展开，成了四个INt元素
      x => {
        x
      }
    )

    mapRDD.collect().foreach(println)


    sc.stop()
  }

}
