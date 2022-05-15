package com.atguigu.bigdata.spark.core.test

import java.io.ObjectInputStream
import java.net.ServerSocket


// server
object Executor {
  def main(args: Array[String]): Unit = {

    // 启动服务器，接收数据
    val server = new ServerSocket(9999)
    println("Server start, waitting for client")

    // 等待客户端的连接
    val client = server.accept()
    val in = client.getInputStream

    val objIn = new ObjectInputStream(in)
    val task = objIn.readObject().asInstanceOf[SubTask]
    val ints = task.compute()

    println("server[9999]: " + ints)

    objIn.close()
    client.close()
    server.close()
  }

}
