package com.atguigu.bigdata.spark.core.rdd.persist

import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_persist {
  def main(args: Array[String]): Unit = {
    // 建立连接

    // cache:将数据临时存储在内存中进行数据重用，数据不安全（内存溢出，机器重启）
    // persist:将数据临时存储在磁盘文件中进行数据重用，涉及到磁盘IO，效率比较低，数据比较安全，程序结束后会被删除
    // checkpoint:数据永久保存在磁盘中，进行数据重用，可以实现跨作业重用，
    //            为了保证数据安全，所以一般情况下会独立执行作业（就是在单独再执行一下checkpoint的那个rdd）
    //            所以一般情况下，会和cache同时使用，用来提高效率（这个RDD不会单独再重复执行）

    // 血缘关系的区别：
    // cache和persist会再血缘关系中添加新的依赖，一旦出现了问题，可以从头读取数据
    // checkpoint会切断血缘关系，并且重新建立血缘关系，因为数据可以从checkpoint的点读取，

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

    mapRDD.cache()
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
