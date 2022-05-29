package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object hive_about {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HIVE_ABOUT")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()


    spark.sql("show tables").show()

    spark.close()
  }

}

