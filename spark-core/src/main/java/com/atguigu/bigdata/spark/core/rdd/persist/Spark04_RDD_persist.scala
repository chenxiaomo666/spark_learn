package com.atguigu.bigdata.spark.core.rdd.persist

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object Spark04_RDD_persist {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    sc.setCheckpointDir("checkPoint")

    val list = List("Hello Scala", "Hello Spark")

    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(word=>{
      println("the function map called")
      (word, 1)
    })

    // 需要落盘，需要指定检查点保存的路径，作业执行完不会被删除
    // 一般保存路径是在分布式存储系统：HDFS
    mapRDD.checkpoint()

    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)

    println("***********************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    // 关闭连接
    sc.stop()


  }
}
