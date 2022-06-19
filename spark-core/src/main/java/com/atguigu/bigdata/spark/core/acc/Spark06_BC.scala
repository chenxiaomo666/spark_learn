package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Spark06_BC {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)


    // Spark中的广播变量就可以将闭包中的数据保存到Executor内存中，广播变量不可更改，是共享只读变量

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val map = mutable.Map(("a", 4), ("b", 5), ("d", 6))

    val bc = sc.broadcast(map)      // 构建广播变量

    val resRDD = rdd1.map(r=>{
      val l = bc.value.getOrElse(r._1, 0)           // bc.value是取广播变量的值
      (r._1, (r._2, l))
    })
    resRDD.collect().foreach(println)



    sc.stop()
  }

}
