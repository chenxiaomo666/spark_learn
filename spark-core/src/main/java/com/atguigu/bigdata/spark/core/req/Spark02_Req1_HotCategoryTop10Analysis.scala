package com.atguigu.bigdata.spark.core.req

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_Req1_HotCategoryTop10Analysis {
  def main(args: Array[String]): Unit = {

    // todo: TOP10热门品类
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparkConf)

    // Q1：actionRDD重复使用
    // Q2： cogroup有可能存在shuffle，性能可能较低，


    // 1、读取原始的日志数据，
    val actionRDD = sc.textFile("datas/user_visit_action.txt")



    // 6、将结果采集到控制台打印

    sc.stop()

  }

}
