def getVersion(version: String): String = {
  val versionRegex                                       = """([0-9]+)(?:(?:\.([0-9]+))?(?:\.([0-9]+))?)?([\-0-9a-zA-Z]*)?""".r
  val versionRegex(major, minor, buildNumber, qualifier) = version
  val buildNumberOverride                                = System.getProperty("packaging.buildNumber")
  val qualifierOverride                                  = System.getProperty("packaging.buildQualifier")
  val finalBuildNumber                                   = if (buildNumberOverride != null) buildNumberOverride else buildNumber
  val finalQualifier                                     = if (qualifierOverride != null) qualifierOverride else qualifier
  s"$major.$minor.$finalBuildNumber$finalQualifier"
}

def isSnapshotBuild(version: String): Boolean = {
  version.endsWith("-SNAPSHOT")
}

val buildVersion = getVersion(ProjectInfo.buildVersion)

version := buildVersion
ThisBuild / version := buildVersion
ThisBuild / credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
ThisBuild / publishTo := {
  val registryUrl = "http://localhost:8081/nexus/"
  if (isSnapshotBuild(buildVersion)) {
    Some("snapshots" at registryUrl + "content/repositories/snapshots/")
  } else {
    Some("releases" at registryUrl + "content/repositories/releases")
  }
}
