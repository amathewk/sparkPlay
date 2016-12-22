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

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.2"
libraryDependencies += "com.databricks" %% "spark-avro" % "3.1.0"

libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.3"

libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.5" % "test")
scalacOptions in Test ++= Seq("-Yrangepos")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}