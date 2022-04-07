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

    val path = "/home/mathysg/Documents/Cours/Data Engineering/Peaceland_Project/Storage/data/storage/part-00000-7df02849-3167-4ea7-8940-90d14a4f51a0-c000.csv"

    val schema = new StructType()
      .add("droneId", StringType)
      .add("peacescore", StringType)
      .add("citizen", StringType)
      .add("lat", StringType)
      .add("lon", StringType)
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
    //df.select("droneId", "peacescore").groupBy("droneId").show()
    // 5
    df.select(avg("peacescore")).where("date>=1649349705").show()
    // 6
    df.select("citizen","people").where("lon.between(48.848370, 48.868370) and lat.between(2.284481,2.304481").show()
    

    spark.close
  }
}
