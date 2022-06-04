package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_operator_Action {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // driver端打印
    rdd.collect().foreach(println)
    println("********************")
    // executor端执行，所以是
    rdd.foreach(println)                   // 直接使用foreach而不使用collect，会不按顺序，

    // 算子：operator(操作)，
    // rdd的方法，和scala集合对象的方法不一样，
    // 集合对象的方法是在同一个节点的内存中完成的
    // rdd是可以将计算逻辑发送到分布式节点（executor）执行的
    // 为了区分不同的处理效果，所以将RDD称之为算子
    //

  }
}
