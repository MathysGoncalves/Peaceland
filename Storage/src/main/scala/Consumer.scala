import org.apache.spark.sql.Row.empty.schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

import java.text.SimpleDateFormat
import java.util.Calendar

object Consumer {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("Peaceland_Project")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._
    val df = spark
      .readStream
      .format("kafka")//"org.apache.spark.sql.kafka010.KafkaSourceProvider")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "report")
      .option("startingOffsets", "earliest")
      .load()

    df.printSchema()

    val fileName = "data/storage/"

    /*
    df.writeStream
      .format("json")
      .option("path", fileName)
      .option("checkpointLocation", "data/checkpoint/")
      .outputMode("append")
      //.trigger(processingTime="10 seconds")
      .start()
    */


    val stringDF = df.selectExpr("CAST(value AS STRING)")

    val schema = new StructType()
      .add("citizen", StringType)
      .add("lat", StringType)
      .add("long", StringType)
      .add("word", StringType)
      .add("people", StringType)
      .add("date", StringType)
      .add("battery", StringType)
      .add("temperature", StringType)
      .add("droneId", StringType)

    val report = stringDF.select(from_json($"value", schema).as("r"))
      .select($"r.citizen",
        $"r.lat",
        $"r.long",
        $"r.word",
        $"r.people",
        $"r.date",
        $"r.battery",
        $"r.temperature",
        $"r.droneID")

    report.writeStream
      .format("console")
      //.option("path", fileName)
      //.option("checkpointLocation", "data/checkpoint/")
      .outputMode("append")
      //.trigger(processingTime="10 seconds")
      .start()
      .awaitTermination()

    spark.stop()

  }
}
