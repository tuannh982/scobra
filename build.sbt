import Sbt.ProjectExtension

lazy val root = (project in file("."))
  .aggregate(
    `example`
  )
  .settings(
    name := "scala-project-template",
    publish / skip := true
  )

lazy val `example` = project
  .defaultSettings()
  .withCrossScalaSupports()

addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
addCommandAlias("fmtCheck", "all scalafmtSbtCheck scalafmtCheckAll")
