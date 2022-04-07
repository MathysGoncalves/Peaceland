import org.apache.spark.sql.Row.empty.schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object Consumer {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("AlertingApp")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val schema = new StructType()
      .add("droneId", StringType)
      .add("peacescore", IntegerType)
      .add("citizen", StringType)
      .add("lat", StringType)
      .add("lon", StringType)
      .add("people", StringType)
      .add("words", StringType)
      .add("date", StringType)

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "report")
      .option("startingOffsets","earliest")
      .load()
      .selectExpr("CAST(value AS STRING)")


    val report = df.select(from_json($"value", schema).as("r"))
      .select($"r.droneId", $"r.peacescore", $"r.citizen", $"r.lat", $"r.lon", $"r.people", $"r.words", $"r.date")

    val final_df = report.filter($"peacescore" >= 75)

    val query = final_df.writeStream
      .format("console")
      .start()

    query.awaitTermination()
    spark.close
  }
}
