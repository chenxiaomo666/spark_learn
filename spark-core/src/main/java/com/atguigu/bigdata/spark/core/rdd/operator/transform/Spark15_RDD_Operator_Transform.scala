package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object Spark15_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)



    val rdd = sc.makeRDD(List(
      ("a", 1), ("b", 3), ("c", 3), ("a", 3)
    ))

    // reduceByKey：相同的Key的数据进行value数据进行聚合操作
    // scala语言中一般的聚合操作都是两两聚合，spark也是，
    val reduceRDD = rdd.reduceByKey((x: Int, y: Int) => {
      println(s"x:${x}, y:${y}")             // 只有key值相同的时候，才参加运算，只有一个的时候没办法进行两两运算
      x + y
    })

    reduceRDD.collect().foreach(println)


    sc.stop()
  }

}
