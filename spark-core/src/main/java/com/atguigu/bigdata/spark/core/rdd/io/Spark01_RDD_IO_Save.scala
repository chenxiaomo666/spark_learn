package com.atguigu.bigdata.spark.core.rdd.io

import com.atguigu.bigdata.spark.core.wc.Spark01_RDD_Part.MyPartitioner
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_IO_Save {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(
      ("a", 1),
      ("b", 2),
      ("c", 3)
    ))

    rdd.saveAsTextFile("output1")
    rdd.saveAsObjectFile("output2")
    rdd.saveAsSequenceFile("output3")  // 必须是键值对的类型RDD才能使用

    // 关闭连接
    sc.stop()
  }

}
