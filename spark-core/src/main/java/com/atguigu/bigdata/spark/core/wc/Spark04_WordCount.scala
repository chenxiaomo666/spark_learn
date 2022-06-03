package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Spark04_WordCount {
  def main(args: Array[String]): Unit = {

    // 建立连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)

    println("#######################01#####################")
    wordcount1(sc)
    println("#######################02#####################")
    wordcount2(sc)
    println("#######################03#####################")
    wordcount3(sc)
    println("#######################04#####################")
    wordcount4(sc)
    println("#######################05#####################")
    wordcount5(sc)
    println("#######################06#####################")
    wordcount6(sc)
    println("#######################07#####################")
    wordcount7(sc)
    println("#######################08#####################")
    wordcount8(sc)
    println("#######################09#####################")
    wordcount9(sc)
//    println("#######################10#####################")
//    wordcount10(sc)
//    println("#######################11#####################")
//    wordcount11(sc)





    // 关闭连接
    sc.stop()
  }

  // groupBy
  def wordcount1(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val group = words.groupBy(word => word)
    println("group: "+group.collect().mkString(", "))
    val wordCount = group.mapValues(iter => iter.size)
    println("result: "+wordCount.collect().mkString(", "))
  }

  // groupByKey
  def wordcount2(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val group = wordOne.groupByKey()
    println("group: "+group.collect().mkString(", "))
    val wordCount = group.mapValues(iter => iter.size)
    println("result: "+wordCount.collect().mkString(", "))
  }

  // reduceByKey
  def wordcount3(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val wordCount = wordOne.reduceByKey(_+_)
    println("wordCount: "+wordCount.collect().mkString(", "))
  }

  // aggregateByKey
  def wordcount4(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val wordCount = wordOne.aggregateByKey(0)(_+_, _+_)
    println("wordCount: "+wordCount.collect().mkString(", "))
  }

  // foldByKey
  def wordcount5(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val wordCount = wordOne.foldByKey(0)(_+_)
    println("wordCount: "+wordCount.collect().mkString(", "))
  }

  // combineByKey
  def wordcount6(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val wordCount = wordOne.combineByKey(
      v=>v,
      (x:Int, y) => x+y,
      (x:Int, y:Int) => x+y
    )
    println("wordCount: "+wordCount.collect().mkString(", "))
  }

  // countByKey
  def wordcount7(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map(x => (x, 1))
    val wordCount = wordOne.countByKey()
    println("wordCount: "+wordCount)
  }

  // countByValue
  def wordcount8(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordCount = words.countByValue()
    println("wordCount: "+wordCount)
  }

  // reduce
  def wordcount9(sc: SparkContext): Unit ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val mapWord = words.map(
      word => {
        mutable.Map[String, Long]((word, 1))
      }
    )
    println(mapWord.collect().mkString(", "))
    val wordCount = mapWord.reduce(
      // 这里reduce合并是，获取如果有相同的key的word，那么就让他的count相加，
      (map1, map2) => {
        map2.foreach {
          case (word, count) => {
            val newCount = map1.getOrElse(word, 0L) + count
            map1.updated(word, newCount)
          }
        }
        map1
      }
    )
    println("wordCount: "+wordCount)
  }
}
