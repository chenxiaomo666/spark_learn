package com.atguigu.bigdata.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Spark01_SparkSQL_Basic {
  def main(args: Array[String]): Unit = {

    // 创建SparkSQL的运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

//    val df = spark.read.json("datas/user.json")
//
//    df.createOrReplaceTempView("user")
//    spark.sql("select avg(age) from user").show()
//
//    df.select("age", "username").show()
//
//    // 在使用DateFrame时，涉及到转换操作，需要引入转换规则，
//    import spark.implicits._
//    df.select($"age"+1).show()


    // DataSet
//    val seq = Seq(1, 2, 3, 4)       // DataFrame是row泛型的DataSet，df有的所有方法，ds都可以用，
//    val ds = seq.toDS
//
//    ds.show()

    // RDD <=> DataFrame
    val rdd = spark.sparkContext.makeRDD(List((1, "zhangsan", 30), (2, "lisi", 40)))

    val df = rdd.toDF("id", "name", "age")  // 这里参数不是必选的，但是有参数比较好
    df.show()

    val rowRDD = df.rdd

    // DataFrame <=> DataSet
    val ds = df.as[User]
    val df1 = ds.toDF()

    // RDD <=> DataSet
    val ds1 = rdd.map(r => {
      User(r._1, r._2, r._3) // 代表将RDD里面的元素转为特定类型的数据User
    }).toDS()

    val userRDD = ds1.rdd

    // 关闭
    spark.close()

  }
  case class User(id: Int, name: String, age: Int)

}
