package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Spark01_SparkSQL_Basic {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    val df = spark.read.json("datas/user.json")

    df.createOrReplaceTempView("user")
    spark.sql("select avg(age) from user").show()

    df.select("age", "username").show()

    // 在使用DateFrame时，涉及到转换操作，需要引入转换规则，
    import spark.implicits._
    df.select($"age"+1).show()


    // DataSet
    val seq = Seq(1, 2, 3, 4)
    val ds = seq.toDS

    ds.show()



    // 关闭
    spark.close()

  }

}
