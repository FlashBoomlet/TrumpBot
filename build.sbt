import sbt.Keys.javaOptions

// scalastyle:off

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

mainClass in (Compile, run) := Some("decisionModel.StateDriver")


lazy val root =
  (project in file(".")).aggregate(
    trumpBotCore
  )

lazy val commonSettings = Seq(
  organization := "com.flashboomlet",
  scalaVersion := "2.11.8",
  resolvers ++= Seq(
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "Typesafe Releases" at "https://repo.typesafe.com/typesafe/maven-releases/",
    "Maven central" at "http://repo1.maven.org/maven2/",
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
  ),
  libraryDependencies ++= Seq(
    "org.reactivemongo" %% "reactivemongo" % "0.11.13",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  )
)

lazy val trumpBotCore = (project in file ("trumpBotCore"))
.settings(commonSettings: _*)
.settings(
  name := "trumpBotCore",
  version := "0.0.0"
)

