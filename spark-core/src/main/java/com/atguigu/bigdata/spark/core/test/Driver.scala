package com.atguigu.bigdata.spark.core.test

import java.io.ObjectOutputStream
import java.net.Socket


// Client
object Driver {
  def main(args: Array[String]): Unit = {

    // 连接服务器
    val client1 = new Socket("localhost", 9999)
    val client2 = new Socket("localhost", 8888)



    val task = new Task()

    val subTask1 = new SubTask()
    val subTask2 = new SubTask()

    subTask1.dates = task.datas.take(2)
    subTask2.dates = task.datas.takeRight(2)

    val out1 = client1.getOutputStream
    val objOut1 = new ObjectOutputStream(out1)

    objOut1.writeObject(subTask1)
    objOut1.flush()
    objOut1.close()
    client1.close()

    val out2 = client2.getOutputStream
    val objOut2 = new ObjectOutputStream(out2)

    objOut2.writeObject(subTask2)
    objOut2.flush()
    objOut2.close()
    client2.close()


  }

}
