package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02_WordCount {
  def main(args: Array[String]): Unit = {

    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)


    // RDD的数据结构，
    val lines: RDD[String] = sc.textFile("datas")
    lines.foreach(println)

    val words = lines.flatMap(_.split(" "))

    val wordToOne = words.map(
      word => (word, 1)
    )

    val wordGroup = wordToOne.groupBy(t => t._1)

    val wordToCount = wordGroup.map{
      case (word, list) => {
        list.reduce(
          (t1, t2) => {
            (t1._1, t1._2+t2._2)
          }
        )
      }
    }

    val array = wordToCount.collect()
    array.foreach(println)


    // 关闭连接
    sc.stop()
  }

}
