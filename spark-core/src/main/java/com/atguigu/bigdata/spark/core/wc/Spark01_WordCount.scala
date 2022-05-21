package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {
  def main(args: Array[String]): Unit = {

    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    // 执行业务操作，
    // 1、读取文件，获取一行一行的数据，
    val lines = sc.textFile("datas/1.txt")

    // 2、将每行的数据进行拆分，形成单词，
    val words = lines.flatMap(_.split(" "))

    // 3、将数据根据单词进行分组，便于统计
    val wordGroup = words.groupBy(word => word)

    // 4、对分组后的数据进行转换，
    val wordToCount = wordGroup.map{
      x=> (x._1, x._2.size)
    }

    // 5、将转换结果控制台打印
     val array = wordToCount.collect()
     array.foreach(println)


    // 关闭连接
    sc.stop()
  }

}
