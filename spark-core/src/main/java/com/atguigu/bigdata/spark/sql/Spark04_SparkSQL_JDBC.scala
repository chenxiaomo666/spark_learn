package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SaveMode, SparkSession}

object Spark04_SparkSQL_JDBC {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    // 读取数据
    val df = spark.read
      .format("jdbc")
      .option("url", "jdbc:mysql://150.158.85.169:3306/spark_learn")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "ch1315203091")
      .option("dbtable", "user")
      .load()


    df.show

    // 保存数据至数据库中
    df.write
      .format("jdbc")
      .option("url", "jdbc:mysql://150.158.85.169:3306/spark_learn")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "ch1315203091")
      .option("dbtable", "user1")
      .mode(SaveMode.Append)
      .save()

    spark.close()
  }

}
