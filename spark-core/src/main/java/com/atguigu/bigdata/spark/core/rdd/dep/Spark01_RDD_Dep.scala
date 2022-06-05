package com.atguigu.bigdata.spark.core.rdd.dep

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Spark01_RDD_Dep {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    val lines = sc.textFile("datas/word.txt")
    println(lines.toDebugString)     // toDebugString：会打印血缘关系
    println("***************************************")

    val words = lines.flatMap(_.split(" "))
    println(words.toDebugString)     // toDebugString：会打印血缘关系
    println("***************************************")

    val wordGroup = words.groupBy(word => word)
    println(wordGroup.toDebugString)     // toDebugString：会打印血缘关系
    println("***************************************")

    val wordToCount = wordGroup.map{
      x=> (x._1, x._2.size)
    }
    println(wordToCount.toDebugString)     // toDebugString：会打印血缘关系
    println("***************************************")

    val array = wordToCount.collect()
    array.foreach(println)

    sc.stop()
  }

}
