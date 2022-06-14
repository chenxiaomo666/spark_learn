package com.atguigu.bigdata.spark.core.rdd.persist

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_persist {
  def main(args: Array[String]): Unit = {
    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    val list = List("Hello Scala", "Hello Spark")

    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(word=>{
      println("the function map called")
      (word, 1)
    })

    // 2
    // 可以将RDD进行持久化操作，这样再次使用该RDD的时候就不需要重新从头弄数据了，
      // cache默认持久化的操作，只能将数据保存至内存中，更快，但是不安全
//    mapRDD.cache()
    // persist可以保存在磁盘中    临时文件，作业执行完会被删除
    mapRDD.persist(StorageLevel.MEMORY_ONLY)   // 不是立刻缓存的，必须在行动算子执行时完成，

    val reduceRDD = mapRDD.reduceByKey(_+_)
    reduceRDD.collect().foreach(println)

    println("***********************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)

    // 1
    // RDD不存储数据，如果一个RDD需要重复使用，那么这个RDD需要从头再执行一边，来获取数据
    // RDD对象可以重复使用，但是数据的从头开始获取，

    // 关闭连接
    sc.stop()


    // 3
    // rdd持久化操作不一定是为了数据重用，在执行时间比较长，或者是数据比较重要的地方也可以使用
  }
}
