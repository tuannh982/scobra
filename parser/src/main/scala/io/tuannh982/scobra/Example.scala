package io.tuannh982.scobra

object Example {

  private class rand extends SubCmd {
    override def use: String         = "rand"
    override def description: String = "generate random number"

    override def execute(): Unit = {
      println(s"random number: ${math.random()}")
    }
  }

  private class echo extends SubCmd {
    override def use: String         = "echo"
    override def description: String = "echo whatever you say"

    private var msg: String = _

    override def init(): Unit = {
      arg[String](Some('m'), "msg", d => msg = d, Some("message to echo"))
    }

    override def execute(): Unit = println(s"you said: $msg")
  }

  private class root extends RootCmd {
    override def use: String         = "example"
    override def description: String = "Example command"

    override def init(): Unit = {
      subCmd(new rand)
      subCmd(new echo)
    }
  }

  def main(args: Array[String]): Unit = {
    val parser = new Parser(new root)
    parser.init()
    parser.execute(args)
  }
}
