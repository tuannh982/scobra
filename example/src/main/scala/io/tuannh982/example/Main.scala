package io.tuannh982.example

object Main {

  def main(args: Array[String]): Unit = {
    val instance = new Example
    println(instance.greeting())
    println(instance.greetingScala())
  }
}
