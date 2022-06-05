package com.atguigu.bigdata.spark.sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

object Spark06_SparkSQL_Test {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    // val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
//    val sc = new SparkContext(sparkConf)

    // 准备数据, 这里尚硅谷的操作是将数据插入到hive中，但是我连不上hive，就是使用dataframe临时表吧。

//    // val rdd_user_visit_action = sc.textFile("datas/user_visit_action.txt").map(r=>r.split(" "))
//    val df_user_visit_action = spark.read.text("datas/user_visit_action.txt")
//    val rdd_user_visit_action = df_user_visit_action.rdd
//    // rdd_user_visit_action.map()
//    val df = spark.read.text("datas/user_visit_action.txt").rdd.map(r=>{
//        val line = r.split("\t")
//        (line(0), line(1).toInt, line(2), line(3).toInt, line(4), line(5), line(6), line(7), line(8), line(9), line(10), line(11), line(12))
//})
//    df.show()
//    val mapRDD = rdd.map(r => {
//      val line = r.split("\t")
//      (line(0), line(1).toInt, line(2), line(3).toInt, line(4), line(5), line(6), line(7), line(8), line(9), line(10), line(11), line(12))
//    })
//
//    mapRDD.toDF().show()

//    sc.stop()
    spark.close()
  }

}
