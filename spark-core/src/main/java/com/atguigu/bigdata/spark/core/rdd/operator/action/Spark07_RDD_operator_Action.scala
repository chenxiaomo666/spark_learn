package com.atguigu.bigdata.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

object Spark07_RDD_operator_Action {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    val user = new User()

    // rdd算子中传递的参数是会包含闭包操作，那么就会进行检测功能，
    // 也就是说，如果rdd是空，不会执行foreach的操作，也需要user进行序列化
    rdd.foreach(num=>{
      println("age: "+(user.age+num))
    })



    sc.stop()

  }

  // 涉及到driver到executor的分发操作，需要对分发对象进行序列化，方法是对class继承Serializable
  // 为什么要序列化：因为user作为一个对象，需要从driver传递到executor，不序列化，没办法网络传递
//  class User extends Serializable {
//    var age = 30
//  }

  // 或者是这样，就不用extends Serializable
  // 样例类在编译的时候，会自动混入序列化特质（实现可序列化接口）
  case class User() {
    val age = 30
  }

}
