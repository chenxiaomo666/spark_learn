package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark08_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // sample
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 2)

    // 传递三个参数
    // 第一个参数表示：抽取后是否放回true(放回),false(不放回)
    // 第二个参数表示：
    //  如果不放回：每条数据被抽取的概率[0, 1]
    //  如果放回：每条数据可能被抽取的次数，可以大于1
    // 第三个参数表示：抽取数据时随机算法的种子，（哪种方法抽取），当种子确定后，抽取的数也确定了，
    // 注：如果seed不传递残烛，那么使用的是当前系统时间，这里就建议不要传递参数，使用系统时间就好

   println( rdd.sample(true, 2 ).collect().mkString(", "))



    sc.stop()
  }

}
