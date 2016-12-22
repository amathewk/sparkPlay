package net.am.play.spark

import java.io.File

import org.specs2.Specification

import scala.io.Source

class CreateAvroSpec extends Specification {

//  def is = s2"""
//
// Can create avro file
//   creates avro file                 $createAvroFile
//                                 """

  def createAvroFile = {
    val f = File.createTempFile("ssfads","")
    AvroUtil.create(f)
    f.exists()
    val content = Source.fromFile(f).mkString
    content must have size(be_>=(0))

  }

  def is = s2"""
   Creates user schema file    $userSchema
   Creates address schema file    $addressSchema
   Creates users in avro file  $createUsersAvro
  Creates addresses in avro file  $createAddressesAvro
      """

//  def is = s2"""
//    """

  def userSchema = {
    val f = File.createTempFile("_sparkPlay", "")
    People.writeUserSchema(f)
    f.exists() must_==(true)
    val content = Source.fromFile(f).mkString
    println(content)
    content must have size(be_>(0))
  }

  def addressSchema = {
    val f = File.createTempFile("_sparkPlay", "")
    People.writeAddressSchema(f)
    f.exists() must_==(true)
    val content = Source.fromFile(f).mkString
    println(content)
    content must have size(be_>(0))
  }

  def createUsersAvro = {
    val f = File.createTempFile("_sparkplay", "")
    People.writeUsers(1000, f)
    import com.sksamuel.avro4s.AvroInputStream

    val is = AvroInputStream.data[User](f)
    val users = is.iterator.toSet
    is.close()
    users must have size(be_==(1000))
  }

  def createAddressesAvro = {
    val f = File.createTempFile("_sparkplay", "")
    People.writeAddresses(1000, f)
    import com.sksamuel.avro4s.AvroInputStream

    val is = AvroInputStream.data[Address](f)
    val addresses = is.iterator.toSet
    is.close()
    addresses must have size(be_==(1000*5))
  }

  def tempMTest = {
    People.tempM
    true
  }
}
