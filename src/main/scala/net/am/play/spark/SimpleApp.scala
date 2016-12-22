package net.am.play.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import com.databricks.spark.avro._
import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]) {
//    processReadme()
    processUsers()
  }


  def processUsers(): Unit = {

    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._

    // The Avro records get converted to Spark types, filtered, and
    // then written back out as Avro records
    val df = spark.read.avro("src/main/resources/users.avro")
    df.filter("favorite_number > 5").write.avro("/tmp/output")
  }

  def processReadme(): Unit = {
    val logFile = "/opt/spark/active/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    sc.stop()

  }
}
