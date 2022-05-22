object Demo {
  def main(args: Array[String]): Unit = {
    val list1 = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val list2 = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j")

    val startI = 3
    val endI = 8

    var result = 0
    (startI to endI).foreach(i=>{
      result = result + list1(i)
    })
    println(result)
  }

}
