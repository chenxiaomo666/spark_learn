package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object Spark05_SparkSQL_Hive {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    // val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    // 使用spark连接外置的hive
    //1.拷贝Hive-size.xml文件到classpath下，
    //2.启用Hive支持，
    //3.增加对应的依赖关系（包含Mysql驱动）

    // 但是我这里使用的是内置的~

    spark.sql("show tables").show()

    spark.close()
  }

}
