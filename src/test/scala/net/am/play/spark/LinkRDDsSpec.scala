package net.am.play.spark

import java.io.File

import org.specs2.Specification

class LinkRDDsSpec extends Specification {

  def is =
    s2"""
       Given an avro data set with users  $createUsersAvroData
       and an avro data set with addresses $createAddressesAvroData
       When the users and addresses are linked  $linkUsersWithAddresses
    """

  def createUsersAvroData = step { People.writeUsers(1000, new File("users.avro")) }
  def createAddressesAvroData = step { People.writeAddresses(1000, new File("addresses.avro")) }
  def linkUsersWithAddresses = step {  }
}
