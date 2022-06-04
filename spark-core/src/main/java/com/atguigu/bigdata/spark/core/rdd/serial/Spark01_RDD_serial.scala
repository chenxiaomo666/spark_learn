package com.atguigu.bigdata.spark.core.rdd.serial

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_serial {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Serial")
    val sc = new SparkContext(sparkConf)

    // 从计算的角度来说，算子以外的代码都是在Driver端执行，算子里面的代码都是在Executor端执行，
    // 从driver端到executor，需要进行网络通信，所以需要序列化，


    sc.stop()
  }

}
