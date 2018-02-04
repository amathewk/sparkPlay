package net.am.play.spark

import com.holdenkarau.spark.testing.{DataFrameSuiteBase, SharedSparkContext}
import org.apache.spark.sql.SaveMode._
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.scalatest.FunSuite

class SampleTest extends FunSuite {
//  with DataFrameSuiteBase {

  val spark = SparkSession.builder().master("local")
      .config("spark.sql.warehouse.dir", "target/hve/")
//      .enableHiveSupport()
    .appName("TEST").getOrCreate()
  val sc = spark.sparkContext
//  val hiveContext  = new org.apache.spark.sql.hive.HiveContext(sparkContext)
//  hiveContext.setConf("hive.metastore.warehouse.dir", $YOUR_LOCATION)

  test("test initializing spark context") {
    val list = List(1, 2, 3, 4)
    val rdd = sc.parallelize(list)

    assert(rdd.count === list.length)
  }

  test("df test") {
    import spark.implicits._
    val ds = spark.createDataset(Seq(Car("Nissan", "Sentra"), Car("Infiniti", "G35")))

    ds.createOrReplaceTempView("CARSVIEW")

//        spark.sql("create database IF NOT EXISTS mydb location 'target/hve/mydb.db'")
        spark.sql("create database IF NOT EXISTS mydb")
//        spark.sql("create table mydb.CARS as select * from CARSVIEW")

    if (spark.catalog.tableExists("mydb", "CARS")) spark.sql("truncate table mydb.CARS")
    if (spark.catalog.tableExists("mydb", "CARS2")) spark.sql("truncate table mydb.CARS2")

    //    ds.toDF().write.format("parquet").option("path", "target/hve/mydb/cars").saveAsTable("CARS")
    ds.toDF().write.format("parquet").mode(Append).saveAsTable("mydb.CARS")
    ds.toDF().write.format("parquet").mode(Append).saveAsTable("mydb.CARS")

    val df = CarSale.read()

    assertResult{4}(df.count)

    val df2 = spark.sql("select * from mydb.CARS2")
    assertResult{4}(df2.count)

    val df3 = spark.read.parquet("target/hve/mydb.db/cars2")
    assertResult{4}(df3.count)

  }


//  override implicit def reuseContextIfPossible: Boolean = true
}

case class Car(make: String, model: String)

object CarSale {
  def read() = {
    val spark = SparkSession.builder().appName("s").getOrCreate()
//    val ds = spark.sql("select * from mydb.CARS")
    val ds = spark.read.parquet("target/hve/mydb.db/cars")
//    val ds = spark.sql("select * from CARS")
    ds.show

    val ds2 = spark.sql("select * from mydb.CARS")

    ds2.show

    ds.write.format("parquet").mode(Append).saveAsTable("mydb.CARS2")

    ds
  }
}