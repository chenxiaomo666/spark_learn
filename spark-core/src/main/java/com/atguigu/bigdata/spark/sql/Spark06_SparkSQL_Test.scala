package com.atguigu.bigdata.spark.sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object Spark06_SparkSQL_Test {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    // val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
//    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
     val sc = new SparkContext(sparkConf)

    // 准备数据, 这里尚硅谷的操作是将数据插入到hive中，但是我连不上hive，就是使用dataframe临时表吧。

//    // val rdd_user_visit_action = sc.textFile("datas/user_visit_action.txt").map(r=>r.split(" "))
//    val df_user_visit_action = spark.read.text("datas/user_visit_action.txt")
//    val rdd_user_visit_action = df_user_visit_action.rdd
//    // rdd_user_visit_action.map()
    val rdd = sc.textFile("datas/user_visit_action.txt")
    val take5RDD = rdd.map(r=>r.split(" ")).take(5)
    println(take5RDD.foreach(println))

    sc.stop()
  }

}
