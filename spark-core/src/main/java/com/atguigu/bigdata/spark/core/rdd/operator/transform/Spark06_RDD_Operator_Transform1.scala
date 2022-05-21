package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark06_RDD_Operator_Transform1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    // groupBy
    val rdd = sc.makeRDD(List(
      "hello","spark","python","hello","scala"
    ), 2)

    val groupRDD = rdd.groupBy(x=>x(0)).map(x=>{
      (x._1+"_key", x._2.map(x=>x+"_word"))
    })

    groupRDD.collect().foreach(println)

//    (p_key,List(python_word))
//    (h_key,List(hello_word, hello_word))
//    (s_key,List(spark_word, scala_word))



    sc.stop()
  }

}
