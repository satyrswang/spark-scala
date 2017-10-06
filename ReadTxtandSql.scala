import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructField, _}
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._

import swiftvis2.plotting._
import swiftvis2.plotting.renderer.FXRenderer

import scalafx.application.JFXApp


object  Sqlpractice extends JFXApp{

    val spark = SparkSession.builder().master("local[*]").appName("NOAA").getOrCreate()
    import spark.implicits._

    spark.sparkContext.setLogLevel("WARN")

    //val data2017 = spark.read.csv("/Users/wyq/Downloads/2017.csv")
    //data2017.schema.printTreeString()

    val tschema = StructType(Array(
      StructField("sid", StringType),
      StructField("date", DateType),
      StructField("mtype", StringType),
      StructField("value", DoubleType)
    ))
    val data2017 = spark.read.schema(tschema)
      .option("dateFormat", "yyyyMMdd").csv("/Users/wyq/Downloads/2017.csv")
    //data2017.show(3)
    val tmax = data2017.filter($"mtype" === "TMAX").drop("mtype").withColumnRenamed("value", "tmax")
    val tmin = data2017.filter('mtype === "TMIN").drop("mtype")
      .withColumnRenamed("value", "tmin").withColumnRenamed("sid", "minsid").withColumnRenamed("date", "mindate")
    //tmax.show(3)
    val combi = tmax.join(tmin, tmax("sid") === tmin("minsid") && tmax("date") === tmin("mindate"))
    //val combi2= tmax.join(tmin,Seq("sid","date"))
    //combi.show(3)
    //combi2.show(3)

    val ave = combi.select('sid, 'date, ('tmax + 'tmin) / 20 * 1.8 + 32 as "ave")
      .groupBy($"sid").agg(avg("ave"))//.agg(Map( "ave" -> "avg")).withColumnRenamed("((((tmax + tmin) / 20) * 1.8) + 32)", "ave")

   // ave.show(5)

    data2017.createOrReplaceTempView("data2017")

//    val sqlsen =spark.sql(
//      """
//        SELECT sid,date,value as tmax FROM data2017 WHERE mtype="TMAX"
//      """.stripMargin)
//    sqlsen.show()
    val sschema = StructType(Array(
      StructField("sid", StringType),
      StructField("lat", DoubleType),
      StructField("lon", DoubleType),
      StructField("name", StringType)
    ))
    val stationRdd = spark.sparkContext.textFile("/Users/wyq/Downloads/ghcnd-stations.txt").map({ line =>
      val id = line.substring(0, 11)
      val lat = line.substring(12, 20).toDouble
      val lon = line.substring(21, 30).toDouble
      val name = line.substring(41, 71)
      Row(id, lat, lon, name)
    })
 // stationRdd.take(5) foreach println

    val stations = spark.createDataFrame(stationRdd, sschema).cache()
  //stations.show()
   val joinedData2017 = ave.join(stations, "sid")
  //joinedData2017.take(5) foreach println
    val localData = joinedData2017.collect()
  localData.take(5) foreach println

    val temps = localData.map(_.getDouble(1))
    val lats = localData.map(_.getDouble(2))
    val lons = localData.map(_.getDouble(3))
    val cg = ColorGradient(0.0 -> BlueARGB, 50.0 -> GreenARGB, 100.0 -> RedARGB)
    val plot = Plot.scatterPlot(lons, lats, "Global Temps", "Longitude", "Latitude",3, temps.map(cg))
    FXRenderer(plot, 800, 600)

    spark.stop()
}
