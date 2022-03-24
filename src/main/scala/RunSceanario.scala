import com.github.tototoshi.csv._
import faker._

import java.io.File

object RunSceanario {

  val r = scala.util.Random
  def seq(p:Product) = p.productIterator.toList

  def dataGen(drone_Id:String) = seq(
    (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1)),
    Address.latitude,
    Address.longitude,
    Lorem.sentence(),
    (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1), (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1))),
    java.time.LocalDate.now,
    15 + r.nextInt(( 99 - 15) + 1),
    10 + r.nextInt(( 30 - 10) + 1),
    drone_Id
  )

  def main(args: Array[String]): Unit = {

    val f = new File("data/event.csv")
    val writer = CSVWriter.open(f)
    val randomReport = (0 to 500).map(x => dataGen(drone_Id = x.toString))
    writer.writeAll(randomReport)
    writer.close()
  }
}