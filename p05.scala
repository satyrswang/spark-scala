/**
 * [main description]
 * @Author   WYQ
 * @DateTime 2017-09-24T08:50:56+0800
 * @return   {[type]}                 [description]
 */


object test{
	def main(args:Array[String]){
		class IntPair(val x: Int, val y: Int)
		object IntPair {
			import math.Ordering
			implicit def ipord: Ordering[IntPair] =
    			Ordering.by(ip => (ip.x, ip.y))
    	}

        import scala.util.Random

		object CustomerID {
  			def apply(name: String) = s"$name--${Random.nextLong}"

  			def unapply(customerID: String): Option[String] = {
    			val name = customerID.split("--").head
    			if (name.nonEmpty) Some(name) else None
    		}
    	}

		val customer1ID = CustomerID("wangyquqing")  
		customer1ID match {
  			case CustomerID(name) => println(name)  
  			case _ => println("Could not extract a CustomerID")
  			}
  		//val CustomerID(name2) = "--asdfasdfasdf"
  		//println(name2)//scala.MatchError: --asdfasdfasdf (of class java.lang.String)


		case class w(a:Int,b:Int)
		var w1 = w(1,2)
		println(w1)
		var w2=w1.copy(_,_)
		println(w2)//shallow copy of w1 cannot use w2.copy
		//var w4=w2.copy(a = 4,b= 3)
		var w3=w1.copy(a = 4,b= 3)
		println(w3)
	}
}

