package io.tuannh982.scobra

import scopt.{OptionDef, OptionParser, Read}

trait Command {
  def use: String
  def description: String
  private[scobra] def optParser: OptionParser[Command]
  private[scobra] def init(parentCmd: Option[Command]): Unit
  def init(): Unit    = {}
  def execute(): Unit = {}

  def arg[T: Read](
    short: Option[Char],
    long: String,
    setter: T => Unit,
    description: Option[String] = None
  ): Unit
  def subCmd(cmd: SubCmd): Unit
}
