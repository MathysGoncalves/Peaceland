import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession

object SparkSessionTest {
  def main(args:Array[String]): Unit ={

    var topic = "stream1"
    var bootstrapServer = "localhost:9092"
    var namenode = "hdfs://localhost:8020"

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("droneStream")
      .getOrCreate();

    println("Test de fonctionnement du stream");
    println("APP Name :"+spark.sparkContext.appName);
    println("Master :"+spark.sparkContext.master);

    val df:DataFrame = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServer)
      .option("subscribe", topic) //selection du topic utilisé
      .option("startingOffsets","earliest")
      .load()

    df.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()

    println("Dataframe has been written")

    spark.sparkContext.stop()
  }
}



