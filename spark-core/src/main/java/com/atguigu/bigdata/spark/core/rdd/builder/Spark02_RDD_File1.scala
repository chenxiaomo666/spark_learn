package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_File1 {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数，
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // 以文件为单位读取数据，元组形式(file_path, file_context)
    val rdd = sc.wholeTextFiles("datas")

    rdd.collect().foreach(println)

//    (file:/C:/Users/陈小陌/Desktop/spark-classes/datas/1.txt,Hello World
//      Hello Spark)
//    (file:/C:/Users/陈小陌/Desktop/spark-classes/datas/2.txt,Hello Scala
//      Hello World
//      Hello Python)
//    (file:/C:/Users/陈小陌/Desktop/spark-classes/datas/test.py,print("帅气的陈小陌")
//
//    )



    // 关闭环境
    sc.stop()


  }

}
