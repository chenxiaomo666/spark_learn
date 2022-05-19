package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_Operator_Transform_Par {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // rdd是一个分区的数据一个一个执行逻辑，只有前面的数据逻辑全部执行完毕后才会执行下一个数据，
    // 分区内的数据执行是有序的，
    // 不同分区执行是无序的，
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numSlices = 2)
    val mapRDD = rdd.map(
      num => {
        println(s"num111111111111: ${num}")
        num
      }
    )

    val mapRDD2 = mapRDD.map(
      num => {
        println(s"num22222222222: ${num}")
        num
      }
    )
    mapRDD2.collect()
    // mapRDD2.collect().foreach(println)


    sc.stop()
  }


}
