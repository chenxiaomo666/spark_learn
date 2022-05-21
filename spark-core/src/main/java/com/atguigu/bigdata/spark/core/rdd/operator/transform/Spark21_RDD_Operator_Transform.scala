package com.atguigu.bigdata.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

object Spark21_RDD_Operator_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)


    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3), ("d", 4)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6), ("e", 5)
    ))

    rdd1.join(rdd2).collect().foreach(println)
//    (a,(1,4))
//    (b,(2,5))
//    (c,(3,6))

    rdd1.leftOuterJoin(rdd2).collect().foreach(println)
//    (d,(4,None))
//    (a,(1,Some(4)))
//    (b,(2,Some(5)))
//    (c,(3,Some(6)))

    rdd1.rightOuterJoin(rdd2).collect().foreach(println)
//    (e,(None,5))
//    (a,(Some(1),4))
//    (b,(Some(2),5))
//    (c,(Some(3),6))

    rdd1.fullOuterJoin(rdd2).collect().foreach(println)
//    (d,(Some(4),None))
//    (e,(None,Some(5)))
//    (a,(Some(1),Some(4)))
//    (b,(Some(2),Some(5)))
//    (c,(Some(3),Some(6)))


    /*
    join
     */





    sc.stop()
  }

}
