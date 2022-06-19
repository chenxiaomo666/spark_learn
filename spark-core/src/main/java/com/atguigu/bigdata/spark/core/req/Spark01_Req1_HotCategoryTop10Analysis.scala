package com.atguigu.bigdata.spark.core.req

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_Req1_HotCategoryTop10Analysis {
  def main(args: Array[String]): Unit = {

    // todo: TOP10热门品类
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("HotCategoryTop10Analysis")
    val sc = new SparkContext(sparkConf)

    // 1、读取原始的日志数据，
    val rdd = sc.textFile("datas/user_visit_action.txt")
    rdd.map(r=>r.split("  ")).collect().foreach(println)

    // 2、统计品类的点击数量（品类ID, 点击数量）

    // 3、统计品类的下单数量（品类ID, 下单数量）

    // 4、统计品类的支付数量（品类ID, 支付数量）

    // 5、将品类进行排序，并取前十名
    //    排序的次序：首先按照点击，再按照下单，再按照支付
    //    元组排序：先比较第一个，如果相同，再比较第二个，再相同比较第三个，以此类推

    // 6、将结果采集到控制台打印

    sc.stop()

  }

}
