package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, functions}

object Spark03_SparkSQL_UDAF2 {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    var df = spark.read.json("datas/user.json")

    // 早期版本中，spark不能在sql（查询语句）中使用强类型的UDAF操作
    // 早期UDAF强类型聚合函数使用DSL语法操作
    val ds = df.as[User]

    // 将UDAF函数转为查询的列对象
    val udafCol = new MyAvgUDAF().toColumn

    ds.select(udafCol).show()

    spark.close()

  }
  /*自定义聚合函数类：计算年龄平均值
  * 1、继承org.apache.spark.sql.expressions.Aggregator
  *  IN:  输入数据类型User
  * BUF: 缓存区的数据类型
  * OUT:输出的数据类型Long
  * */
  case class User(username:String, age:Long)

  case class Buff(var total:Long, var count:Long)   // 样例类
  class MyAvgUDAF extends Aggregator[User, Buff, Long]{
    // 初始值，零值  缓冲区的初始化
    override def zero: Buff = {
      Buff(0L, 0L)
    }

    // 根据输入的数据来更新缓冲区的数据
    override def reduce(b: Buff, a: User): Buff = {
      b.total += a.age
      b.count += 1
      b
    }

    // 合并缓存区
    override def merge(b1: Buff, b2: Buff): Buff = {
      b1.total += b2.total
      b1.count += b2.count
      b1
    }

    // 计算返回结果
    override def finish(reduction: Buff): Long = {
      reduction.total/reduction.count
    }

    // 缓冲区的编码操作，因为是分布式运算，需要在不同节点直接传输数据,
    // 自定义类型是 Encoders.product, scala自带的类型是 Encoders.scalaLong
    override def bufferEncoder: Encoder[Buff] = Encoders.product

    // 同理，是解码操作
    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }

}
