package io.tuannh982.scobra

import scopt.{OptionParser, Read}

class Parser(rootCmd: RootCmd) {
  private var initialized = false

  // noinspection NotImplementedCode
  // format: off
  private class NoMatchCmd extends Command {
    override def use: String                                            = ???
    override def description: String                                    = ???
    override private[scobra] def optParser: OptionParser[Command]       = ???
    override private[scobra] def init(parentCmd: Option[Command]): Unit = ???
    override def init(): Unit                                           = ???
    override def arg[T: Read](short: Option[Char], long: String, setter: T => Unit, description: Option[String]): Unit = ???
    override def subCmd(cmd: SubCmd): Unit = ???
    override def execute(): Unit = rootCmd.optParser.displayToOut(rootCmd.optParser.usage)
  }
  // format: on

  def init(): Unit = synchronized {
    if (!initialized) {
      rootCmd.init(None)
      initialized = true
    }
  }

  def parse(args: Array[String]): Option[Command] = {
    if (!initialized) {
      throw new IllegalStateException("parser is not initialized")
    }
    rootCmd.optParser.parse(args, new NoMatchCmd)
  }

  def execute(args: Array[String]): Unit = {
    parse(args) match {
      case Some(cmd) => cmd.execute()
      case None      => throw new IllegalArgumentException("fail to parse cmd")
    }
  }
}
