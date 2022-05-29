package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object hive_about {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HIVE_ABOUT")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()


    // 内置的hive，
    spark.sql("show tables").show()
//    +--------+---------+-----------+
//    |database|tableName|isTemporary|
//    +--------+---------+-----------+
//    +--------+---------+-----------+

    // 创建一张表
    val df = spark.read.json("datas/user.json")

    df.createOrReplaceTempView("user")
    spark.sql("create table atguigul(id int)")    // 这里测试了一下，在终端里可以执行，但是在代码中不可以执行，不知道为啥，
    spark.sql("show tables").show()

//    +--------+---------+-----------+
//    |database|tableName|isTemporary|
//    +--------+---------+-----------+
//    |        |     user|       true|
//      +--------+---------+-----------+


    // 连接外部的hive，这里我没有hive的环境，配也老费劲了，先不配，这里对目前的工作不是很重要，后期再补，先记录一下，
    // 连接hive，内部和外部的操作没有区别，


    spark.close()
  }

}

