import org.apache.spark.sql.Row.empty.schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object Consumer {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("AlertingApp")
      .getOrCreate()

    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "report")
      .load()

    val final_df = df.filter("peacescore >= 75")

    val query = final_df.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()


  }
}
