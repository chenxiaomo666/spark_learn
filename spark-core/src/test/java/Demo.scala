object Demo {
  def main(args: Array[String]): Unit = {
    val str = "a b c d e"
    val res = str.split(" ")
    val resList = res.toList
    println(res.mkString(", "))
    println(resList.mkString(", "))
  }

}
