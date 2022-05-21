package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark19_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)


    // combineBykey：三个参数
    // 将相同的Key的第一个数据进行结构的转换；
    // 分区内的计算规则，
    // 分区间的计算规则
    val newRDD = rdd.combineByKey(
      v=>(v,1),         // 第一个值不进行运算仅仅是转换结构
      (t:(Int, Int), v) => (t._1+v, t._2+1),
      (t1:(Int, Int), t2:(Int, Int)) => (t1._1+t2._1, t1._2+t2._2)
    )

    newRDD.mapValues(x=>x._1/x._2).collect().foreach(println)



    sc.stop()
  }

}
