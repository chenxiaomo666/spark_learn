package com.atguigu.bigdata.spark.core.test

class SubTask extends Serializable {

  var dates: List[Int] = _

  val logic = (num: Int)=>{num*2}

  def compute()={
    dates.map(logic)
  }

}
