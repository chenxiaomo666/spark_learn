package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark16_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("b", 3), ("c", 3), ("a", 3)
    ))

    // groupByKey：将数据源中的数据，相同key的数据分在一个组中，形成一个对偶元组，
    // 元组的第一个元素是key，第二个元素是相同key值的value的集合
    val groupRDD = rdd.groupByKey()
//    (a,CompactBuffer(1, 3))
//    (b,CompactBuffer(3))
//    (c,CompactBuffer(3))


    // val groupRDD = rdd.groupBy(x=>x._1)
//    (a,CompactBuffer((a,1), (a,3)))
//    (b,CompactBuffer((b,3)))
//    (c,CompactBuffer((c,3)))

    groupRDD.collect().foreach(println)

    sc.stop()
  }

}
