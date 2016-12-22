package net.am.play.spark

import java.io.{File, PrintWriter}

object AvroUtil {

  def create(dest:File): Unit = {
    val pw = new PrintWriter(dest)
    pw.print("fsadf")
    pw.close()
  }
}
