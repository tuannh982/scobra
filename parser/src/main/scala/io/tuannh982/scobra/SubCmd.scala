package io.tuannh982.scobra

import scopt.{OptionDef, OptionParser, Read}

abstract class SubCmd extends Command {
  private[scobra] var _optParser: OptionParser[Command] = _
  private[scobra] var _optDef: OptionDef[Unit, Command] = _

  override final def optParser: OptionParser[Command] = _optParser

  private[scobra] def init(parentCmd: Option[Command]): Unit = {
    parentCmd match {
      case Some(parentCmd) =>
        _optParser = parentCmd.optParser
        // init command
        _optDef = this.optParser
          .cmd(use)
          .text(description)
          .action((_, _) => this)
        // register sub-commands and arguments
        init()
      case None => throw new IllegalStateException("sub command must has parent")
    }
  }

  override final def arg[T: Read](
    short: Option[Char],
    long: String,
    setter: T => Unit,
    description: Option[String]
  ): Unit = {
    var opt = optParser.opt[T](long)
    short.foreach { x =>
      opt = opt.abbr(x.toString)
    }
    description.foreach { x =>
      opt = opt.text(x)
    }
    _optDef.children(opt)
    opt.action((d, _) => { setter(d); this })
  }

  override final def subCmd(cmd: SubCmd): Unit = {
    cmd.init(Some(this))
    this._optDef.children(cmd._optDef)
  }
}
