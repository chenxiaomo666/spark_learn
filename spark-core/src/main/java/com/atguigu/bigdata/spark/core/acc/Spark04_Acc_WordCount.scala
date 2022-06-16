package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

object Spark04_Acc_WordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List("hello", "scala", "spark", "scala", "hello"))

    // 累加器
    // 创建累加器对象
    val wcAcc = new MyAccumulator
    // 向Spark进行注册
    sc.register(wcAcc, name = "wordCountAcc")

    rdd.foreach(word=>{
                              // 数据的累加（使用累加器）
    })

    // 获取累加器的结果
    println(wcAcc.value)

    sc.stop()
  }


  // 自己定义累加器
  // 继承AccumulatorV2
  class MyAccumulator extends AccumulatorV2{
    override def isZero: Boolean = ???

    override def copy(): AccumulatorV2[Nothing, Nothing] = ???

    override def reset(): Unit = ???

    override def add(v: Nothing): Unit = ???

    override def merge(other: AccumulatorV2[Nothing, Nothing]): Unit = ???

    override def value: Nothing = ???
  }

}
