/**
 * [main description]
 * @Author   WYQ
 * @DateTime 2017-10-04T06:08:05+0800
 * @return   {[type]}                 [description]
 */

import scala.collection.immutable.Stream
import scala.io.Source._
import scala.collection.parallel._

case class TempData( day:Int,JD:Int,month:Int,id:String,year:Int,precip:Double,
	snow:Double,tave:Double,tmax:Double,tmin:Double)


object TempData{

	def toDoubleOrNeg(s:String):Double={
		try{
		s.toDouble

		}catch{
			case _:NumberFormatException  => -1
		}	
	}


	def  main(args:Array[String]){

		val f = "/Users/wyq/Downloads/MN212142_9392.csv"
		val source =scala.io.Source.fromFile(f)

		val lines = source.getLines().drop(1)
		//println(source.getLines().length)

		val data = lines.flatMap{line=>
			val p = line.split(",")
			if(p(7)=="." ||p(8)=="."||p(9)==".") Seq.empty else
			Seq(TempData(p(0).toInt,p(1).toInt,p(2).toInt,p(3),p(4).toInt,toDoubleOrNeg(p(5)),
				toDoubleOrNeg(p(6)),toDoubleOrNeg(p(7)),toDoubleOrNeg(p(8)),toDoubleOrNeg(p(9))))
	}.toArray

	source.close()
	
	println(data.length)
	//data.take(5) foreach println
	//println(data(1))
	
	val maxTemp = data.map(_.tmax).max
	val hot = data.filter(_.tmax == maxTemp)
	//println(hot.mkString(" "))
	
	val hotdays = data.maxBy(_.tmax)
	//println(hotdays)

	val hot2 = data.reduceLeft((a,b)=> if(a.tmax >= b.tmax) a else b)
	//println(hot2)

	val rainy = data.filter( t=> t.precip>=1.0)
	val rainy2 = data.count(_.precip>=1.0)
	//println(rainy.length,rainy2)

	val (rainySum,rainyCount) = data.foldLeft(0.0,0){case((sum,cnt),td)=>
		if(td.precip<1.0) (sum,cnt) else (sum+td.tmax, cnt+1)
	}
	println((rainySum,rainyCount))
	val (rainySum2,rainyCount2) = data.par.aggregate(0.0,0)({case((sum,cnt),td)=>
		if(td.precip<1.0) (sum,cnt) else (sum+td.tmax, cnt+1)},{case ((s1,c1),(s2,c2))=>
		(s1+s2,c1+c2) })
	println((rainySum2,rainyCount2))


	val rainytmp = data.flatMap(td => if(td.precip<1.0) Seq.empty else Seq(td.tmax))
	println(rainytmp.sum,rainytmp.length)
	
	val monthGroup = data.groupBy(_.month) //Array
	//println(monthGroup(12)(0))
	val monthlyTemp = monthGroup.map{case(m,days)=>
		m -> days.foldLeft(0.0)((sum,td)=>sum+td.tmax)/days.length
	}
	//println(monthlyTemp.toSeq.sortBy(_._1))
	//monthlyTemp.toSeq.sortBy(_._1) foreach println

	}
}
