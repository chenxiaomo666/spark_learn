package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Spark05_BC {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    // 广播变量，分布式的只读变量
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))

    // join会导致数据量几何增长，并且会影响shuffle性能，不推荐使用
//    val joinRDD = rdd1.join(rdd2)
//    joinRDD.collect().foreach(println)


    // 但是这种map的数据量太大也不行，每个Executor的Task都会被传过去一个map，
    // 闭包数据：事宜Task为单位发送的，每个任务中都会包含闭包数据，
    // 一个Executor（含有多个Task）中含有大量重复数据，会占很多内存，不太合合适
    // 一个Executor就是一个JVM，所以在启动的时候，会自动分配内存，完全可以，
    // 所以将任务中的闭包数据，只在Executor的内存中，达到在Task中的任务共享Executor中的数据
    // 但是对于每个Task，Executor中的数据不可以被更改，因为如果更改会对其他Task造成影响（非要更改，类似于累加器的就不适用于广播变量）

    // Spark中的广播变量就可以将闭包中的数据保存到Executor内存中，广播变量不可更改，是共享只读变量



    val map = mutable.Map(("a", 4), ("b", 5), ("c", 6))

//    val resRDD = rdd1.map {
//      case (w, c) => {
//        val l = map.getOrElse(w, 0L)
//        (w, (c, l))
//      }
//    }
    val resRDD = rdd1.map(r=>{
      val l = map.getOrElse(r._1, 0)           // 和case模式匹配相同
      (r._1, (r._2, l))
    })
    resRDD.collect().foreach(println)

    sc.stop()
  }

}
