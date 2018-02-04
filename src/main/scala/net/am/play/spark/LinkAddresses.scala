package net.am.play.spark

import java.io.File

import org.apache.spark
import org.apache.spark.SparkContext
//import spark.implicits._
import com.databricks.spark.avro._
import org.apache.spark.sql.SparkSession

object LinkAddresses {

  def linkAddresses(userData: File, addressData: File): Unit = {
//    import com.databricks.spark.avro._
    val spark = SparkSession.builder().getOrCreate()
    val users = spark.read.avro(userData.getCanonicalPath()).rdd
    val addresses = spark.read.avro(addressData.getCanonicalPath()).rdd
//    users.map((_.,_))

  }
}
