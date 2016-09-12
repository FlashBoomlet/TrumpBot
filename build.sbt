import sbt.Keys.javaOptions

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
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
  ))

lazy val trumpBotCore = (project in file ("trumpBotCore"))
  .settings(commonSettings: _*)
  .settings(
    name := "trumpBotCore",
    version := "0.0.0"
  )

