package net.am.play.spark

import java.io.File
import java.nio.file.{Path, Paths}

import org.apache.commons.io.FileUtils
import org.apache.log4j.{Level, LogManager}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.functions.udf

object TestData {

  // Pulls our single csv part file from part file directory and deleted directory.
  def extractPartFile(dir: File) = {
    val csvFile = dir.listFiles().find(_.getName.endsWith("csv"))
    if (!csvFile.isDefined) throw new Exception
    val f = csvFile.get
    val destDir = f.getAbsoluteFile.getParentFile.getParentFile
    FileUtils.moveFileToDirectory(f, destDir, false)
//    FileUtils.moveFile(f, new File(f.getAbsoluteFile.getParentFile.getParentFile, dir.getName+"_"))
    FileUtils.deleteDirectory(dir)
    FileUtils.moveFile(new File(destDir, f.getName), new File(destDir, dir.getName))
  }

  def joiner(spark: SparkSession) : DataFrame = {
    import spark.implicits._
    spark.sqlContext.sql("SET spark.sql.autoBroadcastJoinThreshold = 10000")
    spark.sqlContext.sql("SET spark.default.parallelism = 8")

//    val ds = readFile(spark, new File("input.csv"), ",")

//    printPartitionCount("ds", ds)

//    ds.sqlContext.sparkContext.setJobDescription("showing some stuff")

//    ds.show(100000)

    spark.sparkContext.setJobDescription("reading the input")
    val ds = spark.read.option("header", "true").csv("input.csv")

    spark.sparkContext.setJobDescription("reading dsA")
    val dsA = spark.read.option("header", "true").csv("dsA.csv")

//    spark.sparkContext.setJobDescription("reading dsB")
//    val dsB = spark.read.option("header", "true").csv("dsB.csv")

    spark.sparkContext.setJobDescription("joining with dsA")
    val dsJoinA = ds.join(dsA, $"A".equalTo($"i"), "left_outer").withColumnRenamed("o", "AA").drop("i")

//    spark.sparkContext.setJobDescription("joining with dsB")
//    val dsJoinB = dsJoinA.join(dsB, $"B".equalTo($"i")).withColumnRenamed("o", "BB").drop("i")

    spark.sparkContext.setJobDescription("show after join")
    return dsJoinA
//    dsJoinA.show(10000)
//    dsJoinA.queryExecution
  }

  private def printPartitionCount(name:String, ds:DataFrame) = println(s"partition count of $name ${ds.rdd.partitions.length}")

  private def readFile(spark:SparkSession, file: File, sep: String) = {
    val df = spark.read.option("header", "true").text(file.getAbsolutePath)
    df
  }

}

