name := "Peaceland_Project"

version := "1.0"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.0" % "provided" excludeAll(ExclusionRule(organization = "net.jpountz.lz4"))
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8" % "provided"