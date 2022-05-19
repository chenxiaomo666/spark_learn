package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_File_Par {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数，
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // minPartitions：最小分区数量
    // spark是使用的hadoop的文件读取，
    val rdd = sc.textFile("datas/1.txt", minPartitions = 5)

    rdd.saveAsTextFile("output")





    // 关闭环境
    sc.stop()


  }

}
