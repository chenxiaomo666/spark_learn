package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object Spark03_WordCount {
  def main(args: Array[String]): Unit = {

    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)


    val lines = sc.textFile("datas")

    val words = lines.flatMap(_.split(" "))

    val wordToOne = words.map(
      word => (word, 1)
    )

    // spark提供了更多的功能，可以将分组和聚合使用一个方法实现
    // reduceByKey: 相同的key的数据，可以对value进行reduce聚合
    val wordToCount = wordToOne.reduceByKey((x, y)=>{x+y})   // 参数是匿名函数

    val array = wordToCount.collect()
    array.foreach(println)


    // 关闭连接
    sc.stop()
  }

}
