import sbt.Keys.javaOptions

// scalastyle:off

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

mainClass in (Compile, run) := Some("com.flashboomlet.Driver")


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
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "scalac repo" at "https://raw.githubusercontent.com/ScalaConsultants/mvn-repo/master/",
    Resolver.mavenLocal
  )
)


lazy val trumpBotCore = (project in file ("trumpBotCore"))
  .settings(commonSettings: _*)
  .settings(
    name := "trumpBotCore",
    version := "0.0.0",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor_2.11" % "2.4.10",
      "io.scalac" %% "slack-scala-bot-core" % "0.2.1",
      "com.typesafe" % "config" % "1.3.0",
      "org.reactivemongo" %% "reactivemongo" % "0.11.14",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
      "org.scalaj" %% "scalaj-http" % "2.3.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.4",
      "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0",
      "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models",
      "com.github.tototoshi" %% "scala-csv" % "1.3.3"
    )
  )


