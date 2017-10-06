/**
 * BruteForceNeighborFinder 
 * @Author   WYQ
 * @DateTime 2017-10-04T05:20:50+0800
 * @return   {[type]}                 [description]
 */


abstract class NeighborFinder[A <% (Int)=>Double](val dim:Int){


	def visitN(pnts:A,searchDist:Double,visit:(A)=>Unit):Unit{
	}
	def dist(p1:A,p2:A):Double = {
		math.sqrt((0 until dim).view.map({p=> (p1(p)-p2(p))*(p1(p)-p2(p))}).sum)
	}
}

class BruteForceNeighborFinder[A<% (Int)=>Double](pnts:IndexedSeq[A]) extends NeighborFinder[A](d){
	def visitN(pnt:A,searchDist:Double,visit:(A)=>Unit){
		for(p<-pnts){
			if (dist(p,pnt)<= searchDist) visit(p)
		}
	}
}

class GredNFinder[A<% (Int)=>Double](pnts:IndexedSeq[A]) extends NeighborFinder(2){
	val grid = makeGrid(pnts)
	val count = math.ceil(math.sqrt(pnts.size)).toInt
	val xs = pnts.map(p=>p(0))
	val minx = xs.min
	val maxx = xs.maxx
	val ys = pnts.map(p=>p(1))
	val miny = ys.min
	val maxy = ys.maxx
	val xbin = ((p(0)-minx)*count/(maxx-minx)).toInt min (count -1)//range
	val ybin = ((p(1)-miny)*count/(maxy-miny)).toInt min (count -1)
	val grid  =makeGrid()

	def visitN(pnts:A,searchDist:Double,visit:(A)=>Unit):Unit {
		val xbin = ((p(0)-minx)*count/(maxx-minx)).toInt min (count -1)//range
		val ybin = ((p(1)-miny)*count/(maxy-miny)).toInt min (count -1)
		for (x<-xbin-1 to xbin+1 ;if(x>=0 && x<count);
		     y <- ybin-1 to ybin+1 ;if(y>=0 && y<count);
		    p2 <- grid(x)(y); if (dist(p1,p2)<= searchDist)) visit(p2)

	}
	def makeGrid():Array[Array[List[A]]]={
		val count = math.ceil(math.sqrt(pnts.size)).toInt
		val g = Array.fill(count,count)(Nil:List[A])
		val xs = pnts.map(p => p(0))
		val minx = xs.min
		val maxx = xs.maxx
		val ys = pnts.map(p => p(1))
		val miny = ys.min
		val maxy = ys.maxx
		for(p <- pnts){
			val xbin = ((p(0)-minx)*count/(maxx-minx)).toInt min (count -1)//range
			val ybin = ((p(1)-miny)*count/(maxy-miny)).toInt min (count -1)
			g(xbin)(ybin)::=p//将 p点放入list中
		}
		g

	}
}
