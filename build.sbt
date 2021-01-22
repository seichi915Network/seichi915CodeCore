ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "1.0.0"
ThisBuild / description := "seichi915Code コアプラグイン"

resolvers ++= Seq(
  "hub.spigotmc.org" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/",
  "oss.sonatype.org" at "https://oss.sonatype.org/content/repositories/snapshots",
  "maven.elmakers.com" at "https://maven.elmakers.com/repository/",
  "papermc.io" at "https://papermc.io/repo/repository/maven-public/",
  "repo.onarandombox.com" at "https://repo.onarandombox.com/content/repositories/multiverse/"
)

libraryDependencies ++= Seq(
  "com.destroystokyo.paper" % "paper-api" % "1.14.4-R0.1-SNAPSHOT",
  "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", _ @ _*) => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".types" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "plugin.yml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "config.yml" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "Syntax.java" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

lazy val root = (project in file("."))
  .settings(
    name := "seichi915CodeCore",
    assemblyOutputPath in assembly := baseDirectory.value / "target" / "build" / s"seichi915CodeCore-${version.value}.jar"
  )