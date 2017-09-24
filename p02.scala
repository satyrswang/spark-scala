/**
 * [mainly about class]
 * @Author   WYQ
 * @DateAnyime 2017-09-24 03:02:03+0800
 * @return   {[type]}                 [description]
 */

class Point( val _x:Int =2,var y:Int=1 ){ //Parameters without val or var are private values, visible only within the class.
	override def  toString:String = {
		s"($x,$y)"
	}
	def x : Any= _x
}

class User { val x: Long = 987654321 }  //no parameters

// def f[Any](v: Any)(implicit ev: ClassTag[Any]) = ev.toString
/**
 * Notice the special syntax for the setters: 
 * the method has _= appended to the identifier of the getter and the parameters come after.
 */
class Point2{  //no parameters
	private var _x = 0
	private var _y = 0
  	private val bound = 100
	
	def x = _x  //get the private member of class ,x is indentifier of the getter
  	def x_= (newValue: Int): Unit = {
		if (newValue < bound) _x = newValue else printWarning
	}

  	def y = _y
  	def y_= (newValue: Int): Unit = {
    		if (newValue < bound) _y = newValue else printWarning
  	}

  	private def printWarning = println("WARNING: Out of bounds")
}

object test{
	def main(args:Array[String]){
		val x: Long = 987654321
		val y: Float = x  // 9.8765434E8 (note that some precision is lost in this case)

		val face: Char = '☺'
		val number: Int = face  // 9786
		val origin :Point =new Point //必须传入相同格式的参数，除非class构造时已有默认值 new用法
		val a :Point=new Point(face)
		println(a.x);	println(a.y)

		val point1 = new Point2 
		point1.x = 99
		point1.y = 101 //use indentifier of getter to set
		val usr1 = new User
		//point1._x
		//point1._y  // error: cannot be accessed
		println(origin)
		//println(usr1)
	}
}


