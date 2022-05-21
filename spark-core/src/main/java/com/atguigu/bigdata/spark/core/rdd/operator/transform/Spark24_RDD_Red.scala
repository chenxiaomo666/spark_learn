package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark24_RDD_Red {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    /*
    1) 数据准备
    agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔。
    2) 需求描述
    统计出每一个省份每个广告被点击数量排行的 Top3
     */


    // 1.获取原始数据
    val dateRDD = sc.textFile("datas/agent.log")
    // 2.结构的转换方便统计（（省份，广告），1）
    val mapRDD = dateRDD.map(
      line => {
        val datas = line.split(" ")
        ((datas(1), datas(4)), 1)
      }
    )
    // 3.将转换后的结构进行分组聚合，（（省份，广告），sum）
    val reduceRDD = mapRDD.reduceByKey((x, y) => (x + y))      // 这里的x,y都是value，代表同一key下的value两两相加
    // 4.将聚合的结果进行结构的转换，（省份，（广告，sum））
    val newMapRDD = reduceRDD.map(x => (x._1._1, (x._1._2, x._2)))
    // 5.将相同的省份进行分组， （省份，【（广告1，sum），（广告2，sum）】）
    val groupRDD = newMapRDD.groupByKey()
    // 6.将数据进行组内降序排序, 取前三名
    val resultRDD = groupRDD.mapValues(iter => {
      iter.toList.sortBy(x => x._2)(Ordering.Int.reverse).take(3) // 降序
    })
    // 7.打印
    resultRDD.collect().foreach(println)



    sc.stop()
  }

}
