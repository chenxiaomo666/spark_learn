package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object FIleLoadAndSave {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    // SparkSql默认读取和加载文件的方式都是parquet，指spark.read.load()

//    val df = spark.read.load("datas/users.parquet")
//    df.show()

    // 保存数据
//    df.write.save("output")   // 默认保存的是parquet，
//    df.write.format("json").save("output")   // 也可以指定保存格式


    // 尝试使用spark.read.load()读取json
    // val df2 = spark.read.load("datas/user.json")    // 会报错
//    val df2 = spark.read.format("json").load("datas/users.parquet")   // 额，也失败了
//    df2.show()

    // 不创建表直接使用  但我认为这种方法十分的没必要，降低了程序的可读性
    spark.sql("select * from json.`datas/user.json`").show()

    // 保存文件
    val df = spark.read.json("datas/user.json")
    // df.write.format("json").save("output")   // 这里如果文件夹已经存在会报错
    // 这里选择model可以选择append添加和overwrite覆盖和ignore忽略（有该路径就啥也不干，没有就创建写入）
    df.write.format("json").mode("append").save("output")


    // 读取csv文件  以;分割（默认是,分割），设置第一行为表头
    val dfCSV = spark.read.format("csv").option("sep", ";").option("header", "true").option("inferSchema", "true").load("datas/people.csv")
    dfCSV.show()


    spark.close()
  }
}
