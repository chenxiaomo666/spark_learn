package com.atguigu.bigdata.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 分区内和分区间都是_+_，但这里分区是对程序员不可见，唔，应该提出了，稍微淡化分区（事实上应该也是这么默认的），自定义分区另算
    val res = rdd.reduce(_+_)

    println(res)

    var sum = 0
    rdd.foreach(num=>{
      sum+=num
    })
    println(sum)   // 结果是0，不太对劲，


    sc.stop()
  }

}
