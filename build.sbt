name := "figaro"

version := "1.0"

scalaVersion := "2.11.8"
scalaVersion in ThisBuild := "2.11.8"
val figaro = "com.cra.figaro" %% "figaro" % "4.1.0.0"

lazy val commons = (project in file("commons"))
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      figaro
    )
  )

lazy val ch2 = (project in file("ch2")).dependsOn(commons)
