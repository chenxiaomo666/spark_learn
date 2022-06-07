package com.atguigu.bigdata.spark.core.rdd.dep

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_Dep {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    // 新的RDD的一个分区的数据依赖于旧的RDD一个分区的数据，这个依赖称之为OneToOne依赖
    // 新的RDD的一个分区的数据依赖于旧的RDD多个分区的数据，这个依赖称之为Shuffle依赖

    // 窄依赖：表示一个父（上游）RDD的Partition（分区）最多被子（下游）RDD的一个Partition（分区）使用，独生子女
    //        可以不被继承，比如filter算子
    // 宽依赖：表示一个父（上游）RDD的Partition（分区）被子（下游）RDD的多个Partition（分区）使用，多个孩子

    val lines = sc.textFile("datas/word.txt")
    println(lines.dependencies)     // dependencies: rdd之间的依赖关系
    println("***************************************")

    val words = lines.flatMap(_.split(" "))
    println(words.dependencies)
    println("***************************************")

    val wordGroup = words.groupBy(word => word)
    println(wordGroup.dependencies)
    println("***************************************")

    val wordToCount = wordGroup.map{
      x=> (x._1, x._2.size)
    }
    println(wordToCount.dependencies)
    println("***************************************")

    val array = wordToCount.collect()
    array.foreach(println)

    sc.stop()
  }

}
