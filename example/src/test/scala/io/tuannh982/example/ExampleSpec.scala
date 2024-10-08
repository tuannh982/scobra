package io.tuannh982.example

import org.scalatest.flatspec.AnyFlatSpec

class ExampleSpec extends AnyFlatSpec {
  behavior of "Example"

  private val instance = new Example

  it should "correctly greeting" in {
    val str = instance.greeting()
    assert(str == "hello world")
  }
}
