package com.atguigu.bigdata.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

object SPark01_RDD_Memory {
  def main(args: Array[String]): Unit = {
    // 准备环境    *表示当前系统的最大可用核数，
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // 创建RDD
    // 从内存中创建RDD，讲内存中的集合数据，作为处理数据源，应该就是不读取文件的意思
    val seq = Seq[Int](1, 2, 3, 4)
    val seq_list = List(1, 2, 3, 4)

    // parallelize: 并行，同时运行，
    // val rdd = sc.parallelize(seq)   // 需要一个序列就行

    // makeRDD底层调的就是parallelize方法，使用makeRDD比较友好
    val rdd = sc.makeRDD(seq_list)

    rdd.collect().foreach(println)    // 只有运行collect的时候才会实际运行，lazy模式

    // 关闭环境
    sc.stop()


  }

}
