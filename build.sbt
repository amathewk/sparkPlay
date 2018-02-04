name := "sparkplay"

version := "1.0"

scalaVersion := "2.11.7"

//resolvers ++= List(
//  "spray repo" at "http://repo.spray.io/",
//  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
//)

//
//libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "2.0.2",
//  "org.apache.spark" %% "spark-sql" % "2.0.2")

//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"
libraryDependencies += "com.databricks" %% "spark-avro" % "3.1.0"

libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.3"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.9.0" % "test")
scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.2.0_0.8.0" % "test"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.2.0" % "test"



fork in Test := true
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")
parallelExecution in Test := false

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}