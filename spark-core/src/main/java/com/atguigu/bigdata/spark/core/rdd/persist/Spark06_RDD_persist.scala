package com.atguigu.bigdata.spark.core.rdd.persist

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_persist {
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

//    mapRDD.cache()
    mapRDD.checkpoint()

    println(mapRDD.toDebugString)
    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)

    println("***********************")
    println(mapRDD.toDebugString)


    // 关闭连接
    sc.stop()


  }
}
