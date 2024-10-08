name := "parser"

libraryDependencies ++= Dependencies.Testing.All.map(_ % "test")

libraryDependencies += "com.github.scopt" %% "scopt" % Common.scoptVersion
