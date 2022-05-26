package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}

import java.nio.file.attribute.UserDefinedFileAttributeView

object Spark03_SparkSQL_UDAF {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    var df = spark.read.json("datas/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register("ageAvg", new MyAvgUDAF())              // 第一个参数是名称，和下面select保持一致，第二个参数是函数，

    spark.sql("select ageAvg(age) from user").show()

    // 关闭
    spark.close()

  }
  /*自定义聚合函数类：计算年龄平均值
  * 1、继承UserDefinedAggregateFunction
  * 2、重写方法，ctrl+i补充所有需要写的方法
  * 3、弱类型的操作，靠顺序计算
  * */

  class MyAvgUDAF extends UserDefinedAggregateFunction{
    // 输入数据结构
    override def inputSchema: StructType = {
      StructType(
        Array(
          StructField("age", LongType)
        )
      )
    }

    // 缓冲区（计算过程）数据的结构
    override def bufferSchema: StructType = {
      StructType(
        Array(
          StructField("total", LongType),
          StructField("count", LongType)
        )
      )
    }

    // 函数计算结果的类型：OUt
    override def dataType: DataType = LongType

    // 函数的稳定性
    override def deterministic: Boolean = true

    // 缓冲区的初始化
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
//      buffer(0) = 0L
//      buffer(1) = 0L
      buffer.update(0, 0L)
      buffer.update(1, 0L)   // 两种方法都行
    }

    // 根据输入的值，更新缓冲区数据
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer.update(0, buffer.getLong(0)+input.getLong(0))
      buffer.update(1, buffer.getLong(1)+1)
    }

    // 缓冲区数据合并，因为是分布式计算，缓冲区的数据有多份buffer1和buffer2，所以需要合并到一起
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      // scala中人任何两两计算，都以第一个为主，然后结果再作为一个值，和第二个计算，如此往返，直到大厦倾翻，
      buffer1.update(0, buffer1.getLong(0)+buffer2.getLong(0))
      buffer1.update(1, buffer1.getLong(1)+buffer2.getLong(1))

    }

    // 计算平均值
    override def evaluate(buffer: Row): Long = {
      buffer.getLong(0)/buffer.getLong(1)
    }
  }

}
