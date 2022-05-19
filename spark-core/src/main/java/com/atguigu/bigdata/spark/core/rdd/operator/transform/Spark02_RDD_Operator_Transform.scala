package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // map功能强大，但是性能不高（有序）
    // mapPartitions功能强大，一分区为单位进行数据转换操作，将分区的数据加载到内存中进行引用，
    // 但是处理完的数据并不会释放，内存小，数据大的时候，容易存在内存益处，不如map
    // map是来一条处理一条，mapPartitions是整个全部读进内存中在计算
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numSlices = 2)

    val mpRDD = rdd.mapPartitions(
      iter => {
        println(">>>>>>>>>>>>>>")
        iter.map(x => x * 2)
      }
    )

    mpRDD.collect().foreach(println)

    sc.stop()
  }



}
