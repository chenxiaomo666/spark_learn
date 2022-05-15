package com.atguigu.bigdata.spark.core.test

class Task extends Serializable {

  val datas = List(1, 2, 3, 4)

  // val logic = (Int)=> = _*2
  val logic = (num: Int)=>{num*2}

  def compute(): Unit ={
    val res = datas.map(logic)
    println(res)
    res

  }


}
