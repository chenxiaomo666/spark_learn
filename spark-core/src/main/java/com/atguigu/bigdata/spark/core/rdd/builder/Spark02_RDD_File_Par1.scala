package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_File_Par1 {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数，
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)


    // 数据分区的分配，是以行为单位进行读取，spark
    // 和字节数没有关系，hadoop有关系，


    val rdd = sc.textFile("datas/1.txt", minPartitions = 3)

    rdd.saveAsTextFile("output")





    // 关闭环境
    sc.stop()


  }

}
