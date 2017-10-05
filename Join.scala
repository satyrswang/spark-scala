import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import swiftvis2.plotting.renderer.FXRenderer
//import scala.collection.immutable.Stream
//import  org.apache.spark.rdd._
//import scala.collection.parallel._
//import scala.collection.immutable.Stream._

import org.apache.log4j.Logger
import org.apache.log4j.Level

import scalafx.application.JFXApp
import swiftvis2.plotting._
import  swiftvis2.plotting.Plot._


case class  Area(id:String, text:String)

case class Minnesota(id:String,year:Int,period:Int,value:Double)

case class Series(id: String, area_code: String, measure: String, title: String)

object  JoinRddData  {

  def main(args:Array[String]): Unit ={
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("Join").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val areas = sc.textFile("/Users/wyq/Downloads/la.area").filter(!_.contains("area_type_code")).map({ line =>
      val p = line.split("\t").map(_.trim)
      Area(p(1),p(2))
    }).cache()
    //area.take(5) foreach println
    val minn = sc.textFile("/Users/wyq/Downloads/la.data.30.Minnesota").filter(!_.contains("year")).map({ line =>
      val p = line.split("\t").map(_.trim)
      Minnesota(p(0),p(1).toInt,p(2).drop(1).toInt,p(3).toDouble)
    }).cache()

    val data = sc.textFile("/Users/wyq/Downloads/la.series").filter(!_.contains("series_id")).map({ line =>
      val p = line.split("\t").map(_.trim)
      Series(p(0),p(2),p(3),p(6))
    }).cache()

    val rates = minn.filter(_.id.endsWith("03"))
    val decadeGroups = rates.map(d=> (d.id, d.year/10)-> d.value)
    //decadeGroups.take(5) foreach println

    val aven = decadeGroups.aggregateByKey(0.0->0)({case ((s,c),d)=>
      (s+d,c+1) }, { case ((s1,c1),(s2,c2))=>(s1+s2,c1+c2)})
    //aven.take(5) foreach println
    val maxDec = aven.mapValues(t=>t._1/t._2)
      .map({case ((id,dec),av)=>id->(dec*10,av)})
      .reduceByKey({case ((d1,a1),(d2,a2))=> if(a1>a2) (d1,a1) else (d2,a2) })

    maxDec.take(5) foreach println

    val seriresPairs = data.map(s=> s.id->s.title)
    val joinedMaxde =seriresPairs.join(maxDec).mapValues({case (a,(b,c))=>(a,b,c)})
      .map({case (id,t) =>id.drop(3).dropRight(2)->t})

    val full = areas.map(a => a.id -> a.text).join(joinedMaxde)
    full.take(10) foreach println

    //joinedMaxde.take(5) foreach println
    //println(joinedMaxde.getClass)




  sc.stop()

  }

}
