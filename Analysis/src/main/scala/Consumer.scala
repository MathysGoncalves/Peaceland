import org.apache.spark.sql.Row.empty.schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{FloatType, IntegerType,StringType, StructField, StructType}

import java.text.SimpleDateFormat
import java.util.Calendar

object Consumer {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("Peaceland_Project")
      .master("local[*]")
      .getOrCreate()

    val path = "/home/admin/Documents/projet_scala_final2/Peaceland-main/Storage/data/storage/part-00000-2de4d87f-3012-43a6-a265-51744ca63ee5-c000.csv"

    val schema = new StructType()
      .add("droneId", StringType)
      .add("peacescore", IntegerType)
      .add("citizen", StringType)
      .add("lat", FloatType)
      .add("lon", FloatType)
      .add("people", StringType)
      .add("words", StringType)
      .add("date", StringType)

    val df = spark
      .read
      .option("delimiter", ";")
      .schema(schema)
      .csv(path)

    df.printSchema()

    // 1
    df.select(countDistinct("droneId")).show()
    // 2
    df.select("citizen", "peacescore").where("peacescore>= 75").show()
    // 3
    df.select("peacescore").orderBy(desc("peacescore")).show()
    // 4
    //df.withColumn("peacescore",col("peacescore").cast("int")).select(avg("peacescore")).where("date>=1649349705").show()
    // 5
    df.select("citizen","people").where(col("lon").between(48.848370, 48.868370) && col("lat").between(2.284481,2.304481)).show()
    // 6
    df.withColumn("peacescore",col("peacescore").cast("int")).groupBy("citizen").agg(
      max("peacescore"),
      mean("peacescore"),
      min("peacescore")).show()
    // 7


    spark.close
  }
}
