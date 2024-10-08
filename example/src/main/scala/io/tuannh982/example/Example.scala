package io.tuannh982.example

class Example extends ExampleTrait {

  def greeting(): String = {
    "hello world"
  }

  def greetingScala(): String = {
    s"hello $source"
  }
}
