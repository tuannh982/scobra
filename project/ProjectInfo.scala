import sbt.*
import sbt.Keys.*

object ProjectInfo {
  val organization                    = "io.tuannh982"
  val buildVersion                    = "0.0.1-SNAPSHOT"
  val scala212Version                 = "2.12.17"
  val scala213Version                 = "2.13.12"
  val scala3Version                   = "3.3.3"
  val scalaVersion: String            = scala212Version
  val crossScalaVersions: Seq[String] = Seq(scala212Version, scala213Version, scala3Version)
}

object Dependencies {

  object Testing {
    private val ScalaTestVersion = "3.2.12"
    val ScalaTest: Seq[ModuleID] = Seq("org.scalatest" %% "scalatest" % ScalaTestVersion)
    val All: Seq[ModuleID]       = ScalaTest
  }
}

object Sbt {

  implicit class ProjectExtension(project: Project) {

    def defaultSettings(): Project = {
      project
        .settings(
          organization := ProjectInfo.organization,
          scalaVersion := ProjectInfo.scalaVersion,
          Test / fork := true
        )
    }

    def withCrossScalaSupports(): Project = {
      project
        .settings(
          crossScalaVersions := ProjectInfo.crossScalaVersions,
          Compile / unmanagedSourceDirectories ++= {
            (Compile / unmanagedSourceDirectories).value.map { dir =>
              CrossVersion.partialVersion(scalaVersion.value) match {
                case Some((2, 12)) => file(dir.getPath ++ "-2.12")
                case Some((2, 13)) => file(dir.getPath ++ "-2.13")
                case Some((3, _))  => file(dir.getPath ++ "-3")
                case unknown       => throw new IllegalStateException(s"Scala version $unknown is unsupported")
              }
            }
          },
          scalacOptions := {
            CrossVersion.partialVersion(scalaVersion.value) match {
              case Some((3, _)) =>
                Seq(
                  "-explain"
                )
              case _ => Seq.empty
            }
          }
        )
    }
  }
}
