package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_File {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数，
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // 从文件中创建RDD，将文件中的数据作为处理数据源
    // path默认是当前环境的根路径作为基准，可以写绝对路径，也可以是相对路径
    // sc.textFile("C:\\Users\\陈小陌\\Desktop\\spark-classes\\datas\\2.txt")
     val rdd = sc.textFile("datas")    // 如果不写，默认是全部文件
    // val rdd = sc.textFile("datas/1.txt")
    // 也可以是hdfs文件
    // val rdd = sc.textFile("hdfs://127.0.0.1:8020/test.txt")
    rdd.collect().foreach(println)



    // 关闭环境
    sc.stop()


  }

}
