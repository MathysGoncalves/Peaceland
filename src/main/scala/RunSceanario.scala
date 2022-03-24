import com.github.tototoshi.csv._
import faker._

import java.io.File
import java.util.Date

case class Event(
                  val citizen: String,
                  val peacescore: Int,
                  val latitude: Double,
                  val longitude: Double,
                  val wordHeard: String,
                  val date: Date,
                  val battery: Int,
                  val temperature: Int,
                  val droneId:String,
                )

object RunSceanario {

  val r = scala.util.Random

  def seq(p:Product) = p.productIterator.toList

  def dataGen(rows:Int, drone_Id:String) = seq(java.util.UUID.randomUUID.toString,
    3 + r.nextInt(( 97 - 3) + 1),
    Address.latitude,
    Address.longitude,
    Lorem.sentence(),
    java.time.LocalDate.now,
    15 + r.nextInt(( 99 - 15) + 1),
    10 + r.nextInt(( 30 - 10) + 1),
    drone_Id
  )

  def main(args: Array[String]): Unit = {

    val p1 = dataGen(2,"1")
    val p2 = dataGen(2,"2")
    val f = new File("data/event.csv")
    val writer = CSVWriter.open(f)
    writer.writeAll(List(p1, p2))
    writer.close()
  }
}