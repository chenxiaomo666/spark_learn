package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object Spark14_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // Key-Value类型
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
    val mapRDD = rdd.map(x=>(x, "cxm"))

    // 根据指定的分区规则对数据进行重分区，
    mapRDD.partitionBy(new HashPartitioner(2)).saveAsTextFile("output")           // partitionBy不是RDD的方法，这里是使用的隐式转换

    sc.stop()
  }

}
