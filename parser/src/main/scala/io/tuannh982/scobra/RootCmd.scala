package io.tuannh982.scobra

import scopt.{OptionParser, Read}

abstract class RootCmd extends Command {

  private[scobra] final val optParser: OptionParser[Command] = new OptionParser[Command](use) {
    head(description)
    override def showUsageOnError: Option[Boolean] = Some(true)
  }

  override final private[scobra] def init(parentCmd: Option[Command]): Unit = {
    init()
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
    opt.action((d, _) => { setter(d); this })
  }

  override final def subCmd(cmd: SubCmd): Unit = {
    cmd.init(Some(this))
  }
}
