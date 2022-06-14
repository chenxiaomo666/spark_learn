package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Spark01_RDD_Part {
  def main(args: Array[String]): Unit = {

    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(
      ("nba", "XXXXXXXXXXXX"),
      ("wnba", "xxxxxxxxx"),
      ("nba", "xxxxxxxxxxx"),
      ("cba", "xxxxxxxxxxx")
    ), 3)

    val partRDD = rdd.partitionBy(new MyPartitioner)

    partRDD.saveAsTextFile("output")


    // 关闭连接
    sc.stop()
  }

  // 自定义分区
  // 继承Partitioner
  // 重新方法
  class MyPartitioner extends Partitioner{
    // 分区数量
    override def numPartitions: Int = 3

    // 根据数据的key值返回数据的分区索引（从0开始）
    // ，
    override def getPartition(key: Any): Int = {

      key match {
        case "nba" => 0
        case "wnba" => 1
        case _ => 2
      }

//      if(key=="nba"){
//        0
//      } else if(key=="wnba"){
//        1
//      }else if(key=="cba"){
//        2
//      }else{
//        2
//      }
    }
  }

}
