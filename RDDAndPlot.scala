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


case class TempData( day:Int,JD:Int,month:Int,id:String,year:Int,precip:Double,
                     snow:Double,tave:Double,tmax:Double,tmin:Double)


object  RDDTempData extends JFXApp{

  def toDoubleOrNeg(s:String):Double={
    try{
      s.toDouble

    }catch{
      case _:NumberFormatException  => -1
    }
  }

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("Temp Data").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("/Users/wyq/Downloads/MN212142_9392.csv")
    //lines.take(5) foreach println

    val data = lines.filter(!_.contains("Day")).flatMap{line=>
      val p = line.split(",")
      if(p(7)=="." ||p(8)=="."||p(9)==".") Seq.empty else
        Seq(TempData(p(0).toInt,p(1).toInt,p(2).toInt,p(3),p(4).toInt,toDoubleOrNeg(p(5)),
          toDoubleOrNeg(p(6)),toDoubleOrNeg(p(7)),toDoubleOrNeg(p(8)),toDoubleOrNeg(p(9))))
    }.cache()
    //println(data.count())
    //println(data.max()(Ordering.by(_.tmax)))
    //println(data.reduce((td1,td2)=> if(td1.tmax>= td2.tmax) td1 else td2))
    val maxTemp = data.map(_.tmax).max
    val hot = data.filter(_.tmax == maxTemp)

    //println(hot.collect().mkString(", "))


    val rainy = data.filter( t=> t.precip>=1.0).count()
    //println(rainy)

    val (rainySum2,rainyCount2) = data.aggregate(0.0,0)({case((sum,cnt),td)=>
      if(td.precip<1.0) (sum,cnt) else (sum+td.tmax, cnt+1)},{case ((s1,c1),(s2,c2))=>
      (s1+s2,c1+c2) })
    //println((rainySum2,rainyCount2))


    val  keyByYear = data.map(td=>td.year -> td)
    val averagebyyear = keyByYear.aggregateByKey(0.0 -> 0)({case ((sum,cunt),td)=>
      (sum + td.tmax,cunt +1)},{case ((s1,c1),(s2,c2))=>(s1+s2,c1+c2)})
    println (averagebyyear)

    val monthG = data.groupBy(_.month)
    val monthlyhigh = monthG.map({case(m,tda)=>
      m -> tda.foldLeft(0.0)((sum,td)=>sum+td.tmax)/tda.size})// k,v and operations on v,
    //monthlyhigh.collect.sortBy(_._1) foreach println
    //val monthlyhight = monthlyhigh.collect()
    //println(monthlyhight.getClass())
    //println(monthlyhight.map(_._1).getClass())

    val monthlylow = monthG.map({case(m,tda)=>
      m -> tda.foldLeft(0.0)((sum,td)=>sum+td.tmin)/tda.size})



//    val plot = Plot.scatterPlots(Seq((monthlyhigh.map(_._1).collect(), monthlyhigh.map(_._2).collect(),
//      0xffff0000,5) , (monthlylow.map(_._1).collect(), monthlylow.map(_._2).collect(),
//      0xff0000ff,5)), "Tmp","month","temp")
//    // plot.render()
//    FXRenderer(plot,800,600)

    println(data.map(_.tmax).stdev())
    println(data.map(_.tmin).stdev())
    println(data.map(_.tave).stdev())
    println(data.map(_.tave).stats())
//
//    val bins = (-20.0 to 107.0 by 1.0).toArray
//    val counts = data.map(_.tmax).histogram(bins,true)
//    println(counts)
//    val hist = Plot.histogramPlot(bins,counts,RedARGB,false,"Tmp")
//    FXRenderer(hist)

    val averagedata = averagebyyear.collect().sortBy(_._1)

    val longTermPlot = Plot.scatterPlotWithLines(averagedata.map(_._1), averagedata.map{case (_,(s,c))=>
      s/c} ,symbolSize = 0,symbolColor = BlueARGB, lineGrouping = 1)
    FXRenderer(longTermPlot,800,600)
}
