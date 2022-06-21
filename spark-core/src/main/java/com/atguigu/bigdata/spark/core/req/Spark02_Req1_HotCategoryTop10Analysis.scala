package com.atguigu.bigdata.spark.core.req

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_Req1_HotCategoryTop10Analysis {
  def main(args: Array[String]): Unit = {

    // todo: TOP10热门品类
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparkConf)

    // Q1：actionRDD重复使用
    // Q2： cogroup有可能存在shuffle，性能可能较低，


    // 1、读取原始的日志数据，
    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    actionRDD.cache()       // 储存一个在内存中，不用再次都读文件

    // 2、统计品类的点击数量（品类ID, 点击数量）
    val clickActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("\t")
        datas(6) != "-1"
      }
    )

    val clickCountRDD = clickActionRDD.map(
      action => {
        val datas = action.split("\t")
        (datas(6), 1)
      }
    ).reduceByKey(_ + _)         // 对于分区内和分区间都是采用相同Key的值相加的算法
    println("**************点击***********************")
    println(clickCountRDD.collect().mkString)


    // 3、统计品类的下单数量（品类ID, 下单数量）
    val orderActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("\t")
        datas(8) != "\\N"
      }
    )

    val orderCountRDD = orderActionRDD.flatMap(
      action => {
        val datas = action.split("\t")
        val cid = datas(8)
        val cids = cid.split(",")
        cids.map(id=>(id, 1))
      }
    ).reduceByKey(_ + _)
    println("**************下单***********************")
    println(orderCountRDD.collect().mkString)

    // 4、统计品类的支付数量（品类ID, 支付数量）
    val payActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("\t")
        datas(10) != "\\N"
      }
    )

    val payCountRDD = payActionRDD.flatMap(
      action => {
        val datas = action.split("\t")
        val cid = datas(10)
        val cids = cid.split(",")
        cids.map(id=>(id, 1))
      }
    ).reduceByKey(_ + _)
    println("**************支付***********************")
    println(payCountRDD.collect().mkString)

    // 5、将品类进行排序，并取前十名
    //    排序的次序：首先按照点击，再按照下单，再按照支付
    //    元组排序：先比较第一个，如果相同，再比较第二个，再相同比较第三个，以此类推
    //    (品类id，（点击数量， 下单数量， 支付数量）)

    val cogroupRDD = clickCountRDD.cogroup(orderCountRDD, payCountRDD)
    val analysisRDD = cogroupRDD.mapValues(r=>{

      val clickIter = r._1.iterator
      val orderIter = r._2.iterator
      val payIter = r._3.iterator

      var clickCount = 0
      var orderCount = 0
      var payCount = 0

      if(clickIter.hasNext){               // 如果这迭代器还可以迭代的话
        clickCount = clickIter.next()      // 取该迭代器下一个值
      }

      if(orderIter.hasNext){
        orderCount = orderIter.next()
      }

      if (payIter.hasNext){
        payCount = payIter.next()
      }

      (clickCount, orderCount, payCount)

      }
    ).sortBy(_._2, ascending=false).take(10)   // 元组排序：先比较第一个，如果相同，再比较第二个，再相同比较第三个，以此类推
    println("**************聚合***********************")
    println(analysisRDD.mkString)    // take也是一个采集操作，所以这里不需要collect


    // 6、将结果采集到控制台打印

    sc.stop()

  }

}
