package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark17_RDD_Operator_Transform3 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)


    // 计算相同key的平均值
    val aggRDD = rdd.aggregateByKey((0,0))(   // t是根据(0,0)定义的
      (t, v) => (t._1+v, t._2+1),       // t(sum, count)  t第一个是累计和，第二个是key出现的次数，
      (t1, t2) =>(t1._1+t2._1, t2._2+t1._2)
    )

    // key同时也保留
    aggRDD.mapValues(x=>x._1/x._2).collect().foreach(println)



    sc.stop()
  }

}
