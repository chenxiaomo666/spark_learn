package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

object Spark04_SparkSQL_JDBC {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    val df = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://150.158.85.169:3306")
      .option("driver", "com.mysql.jdbc.Driver")
      .option("user", "root")
      .option("password", "ch1315203091")
      .option("dbtable", "spark_learn")
      .load()

    df.show()

    spark.close()
  }

}
