package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object SPark01_RDD_Memory_Par {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数,
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    sparkConf.set("spark.default.parallelism", "3")  // 是可配的，默认并行度，
    val sc = new SparkContext(sparkConf)

    // RDD的并行度
    val rdd = sc.makeRDD(
      (1 to 5)           // numSlices = 2 代表两个分区,默认是defaultParallelism(默认并行度：和local[*]保持一致？,当前是20个)
    )

    rdd.saveAsTextFile("output")

    // 关闭环境
    sc.stop()


  }

}
