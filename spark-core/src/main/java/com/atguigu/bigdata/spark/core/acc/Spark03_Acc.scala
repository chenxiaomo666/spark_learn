package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object Spark03_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 累加器用来把Executor端的变量信息聚合到Driver端，
    // 在Driver程序中定义的变量，在Executor端的每个Task都会得到这个变量的一个新的副本，
    // 在每个task更新这些副本信息的值后，传回到Driver端进行merge

    // 获取系统的累加器，Spark默认就提供了简单数据聚合的累加器，
    val sumAcc = sc.longAccumulator("sum")
    val mapRDD = rdd.map(num => {
      // 使用累加器
      sumAcc.add(num)
      num
    })

    // 如果累加器在转换算子中，rdd没有执行行动算子，是不会触发转换算子的，所以会出现少加或者多加的情况，
    // 多加和少加取决于转换算子被执行的次数，这很好理解~
    // 所以一般情况下，累加器会放在行动算子中操作，

    // 获取累加器的值

    mapRDD.collect()
    println(sumAcc.value)


    sc.stop()
  }

}
