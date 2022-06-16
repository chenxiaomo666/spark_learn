package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 累加器用来把Executor端的变量信息聚合到Driver端，
    // 在Driver程序中定义的变量，在Executor端的每个Task都会得到这个变量的一个新的副本，
    // 在每个task更新这些副本信息的值后，传回到Driver端进行merge

    // 获取系统的累加器，Spark默认就提供了简单数据聚合的累加器，
    val sumAcc = sc.longAccumulator("sum")
    rdd.foreach(num=>{
      // 使用累加器
      sumAcc.add(num)
    })

    // 获取累加器的值
    println(sumAcc.value)


    sc.stop()
  }

}
