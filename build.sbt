import Sbt.ProjectExtension

lazy val root = (project in file("."))
  .aggregate(
    `parser`
  )
  .settings(
    name := "scobra",
    publish / skip := true
  )

lazy val `parser` = project
  .defaultSettings()
  .withCrossScalaSupports()

addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
addCommandAlias("fmtCheck", "all scalafmtSbtCheck scalafmtCheckAll")
