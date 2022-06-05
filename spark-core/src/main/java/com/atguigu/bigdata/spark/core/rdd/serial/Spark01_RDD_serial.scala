package com.atguigu.bigdata.spark.core.rdd.serial

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD_serial {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Serial")
    val sc = new SparkContext(sparkConf)

    // 从计算的角度来说，算子以外的代码都是在Driver端执行，算子里面的代码都是在Executor端执行，
    // 从driver端到executor，需要进行网络通信，所以需要序列化，
    // 算子内经常会用到算子外的数据，这样就形成了闭包的效果，
    // 如果使用的算子外数据无法序列化，就意味着无法传值给executor
    // 就会发生错误，
    // 所以在执行任务前，检测闭包内的对象是否可以进行序列化，这个操作称之为闭包检测

    val rdd = sc.makeRDD(Array("hello world", "hello spark", "hive", "aiguigu"))
    val search = new Search("h")
    search.getMatch1(rdd).collect().foreach(println)


    sc.stop()
  }

  // 查询对象，查询对象元
  // 类的构造参数，是类的属性
  class Search(query: String) extends Serializable {
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    // 函数序列化案例
    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    // 属性序列化案例
    def getMatch2(rdd: RDD[String]): RDD[String] = {
      rdd.filter(x=>x.contains(query))
    }
  }

}
