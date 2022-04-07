import org.apache.spark.sql.Row.empty.schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
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

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "report")
      .option("startingOffsets","earliest")
      .load()

    val report = df.selectExpr("CAST(value AS STRING)")

    val query = report.writeStream
      .trigger(Trigger.ProcessingTime("30 seconds"))
      .option("header","false")
      .option("delimiter","|")
      .format("csv")
      .option("checkpointLocation", "data/checkpoints")
      .option("path", "data/storage")
      .start()

    query.awaitTermination()
    spark.close
  }
}
