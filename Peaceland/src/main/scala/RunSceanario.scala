import com.github.tototoshi.csv._
import faker._

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer

import scala.io.Source
import java.io.File

object RunSceanario {

  val props: Properties = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

  val producer = new KafkaProducer[String, String](props)
  val topic = "report"

  def sendRecord(kafkaproducer : KafkaProducer[String, String]) {

    val report = Reports.create()
    val record = new ProducerRecord[String, String](topic, report.droneId.toString, Reports.report_toString(report))
    val metadata=producer.send(record)

    printf(s"sent record(key=%s value=%s) " +
      "meta(partition=%d, offset=%d)\n",
      record.key(), record.value(),
      metadata.get().partition(),
      metadata.get().offset())

    Thread.sleep(2000)
    sendRecord(kafkaproducer)
  }

  def main(args: Array[String]): Unit = {
    sendRecord(producer)
  }

}

