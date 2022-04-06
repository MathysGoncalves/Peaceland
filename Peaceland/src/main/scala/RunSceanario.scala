import com.github.tototoshi.csv._
import faker._

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

import scala.io.Source
import java.io.File

object RunSceanario {

  val r = scala.util.Random
  def seq(p:Product) = p.productIterator.toList

  def dataGen() = seq(
    (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1)),
    Address.latitude,
    Address.longitude,
    Lorem.sentence(),
    (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1), (java.util.UUID.randomUUID.toString, 3 + r.nextInt(( 97 - 3) + 1))),
    java.time.LocalDate.now,
    15 + r.nextInt(( 99 - 15) + 1),
    10 + r.nextInt(( 30 - 10) + 1),
    r.nextInt(( 30 - 10) + 1)
  )

  implicit object MyFormat extends DefaultCSVFormat {
    override val delimiter = ';'
  }

  def main(args: Array[String]): Unit = {

    val fileName = "data/event.csv"
    val f = new File(fileName)
    val writer = CSVWriter.open(f)
    val randomReport = (0 to 500).map(x => dataGen())
    writer.writeAll(randomReport)
    writer.close()

    val props: Properties = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

    val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)
    val topicName = "report"

    for (line <- Source.fromFile(fileName).getLines()) {
      val key = line.split(",") {
        0
      }
      val record: ProducerRecord[String, String] = new ProducerRecord[String, String](topicName, key, line)

      producer.send(record, (recordMetadata: RecordMetadata, exception: Exception) => {
        if (exception != null) {
          exception.printStackTrace()
        } else {
          println(s"Meta: $recordMetadata")
        }
      })
    }
    producer.close()
  }
}
