package net.am.play.spark

import java.io.{File, PrintWriter}


case class User(name: String, gender: String, addresses: Option[List[Address]])

case class Address(name: String, street: String, city: String, zip: String)

object People {

  def writeUserSchema(file:File): Unit = {
    val pw = new PrintWriter(file)
    import com.sksamuel.avro4s.AvroSchema
    pw.print(AvroSchema[User].toString(true))
    pw.close()
  }

  def writeAddressSchema(file:File): Unit = {
    val pw = new PrintWriter(file)
    import com.sksamuel.avro4s.AvroSchema
    pw.print(AvroSchema[Address].toString(true))
    pw.close()
  }

//  def writeSchema[T](file:File): Unit = {
//    val pw = new PrintWriter(file)
//    import com.sksamuel.avro4s.AvroSchema
//    pw.print(AvroSchema[T].toString(true))
//    pw.close()
//  }

  def writeUsers(count: Int, file: File): Unit = {
    val dummyUsers = Iterator.range(0,count).map(dummyUser(_))
    import com.sksamuel.avro4s.AvroOutputStream
    val os = AvroOutputStream.data[User](file)
    (dummyUsers grouped 10000) foreach os.write
    os.flush
    os.close
  }

  def writeAddresses(count: Int, file: File) = {
    val dummyAddresses = Iterator.range(0,count).flatMap(dummyUserAddresses(_))
    import com.sksamuel.avro4s.AvroOutputStream
    val os = AvroOutputStream.data[Address](file)
    (dummyAddresses grouped 10000) foreach os.write
    os.flush
    os.close
  }


  def tempM = {
    val dummyUsers = Iterator.range(1,1000000).map(dummyUser(_))
    dummyUsers grouped 1000 foreach println
//    dummyUsers foreach println
  }

  def dummyUser(suffix:Int):User = {
    User(s"name_$suffix", "F", None)
  }

  def dummyUserAddresses(suffix:Int):Seq[Address] = {
    (0 until 5).map(n => Address(s"name_${suffix}", s"street_${suffix}_$n", s"city_${suffix}_$n", s"zip_${suffix}_$n"))
  }
}

