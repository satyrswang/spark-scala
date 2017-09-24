/**
 * [mainly about class]
 * @Author   WYQ
 * @DateAnyime 2017-09-24 03:02:03+0800
 * @return   {[type]}                 [description]
 */

class Point( val _x:Int =2,var y:Int=1 ){// 构造时写var与不写var区别？无var则不成为Point对象成员值
	//Parameters without val or var are private values, visible only within the class.
	override def  toString:String = {
		s"($x,$y)"
	}
	def x : Any= _x
}

class User { val x: Long = 987654321 } 

//def User//error
 
// import scala.reflect.ClassTag
// def func[Any](v: Any) = v match {
//   			case _: Int    => "Int"
//   			case _: String => "String"
//   			case _         => "Unknown"
// }

// def f[Any](v: Any)(implicit ev: ClassTag[Any]) = ev.toString

class Point2{
	 private var _x = 0
  private var _y = 0
  private val bound = 100

  def x = _x
  def x_= (newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }

  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }

  private def printWarning = println("WARNING: Out of bounds")
}
//私有数据在_x 有x方法来取数 
/**
 * Notice the special syntax for the setters: 
 * the method has _= appended to the identifier of the getter and the parameters come after.
 */

object test{
	def main(args:Array[String]){
		
		val x: Long = 987654321
		val y: Float = x  // 9.8765434E8 (note that some precision is lost in this case)

		val face: Char = '☺'
		val number: Int = face  // 9786
		val origin :Point =new Point //必须传入相同格式的参数，除非class构造时已有默认值
		val a :Point=new Point(face)
		println(a.x);	println(a.y)

		val point1 = new Point2 //无问题
		point1.x = 99
		point1.y = 101 // prints the warning
		val usr1 = new User
		
		println(origin)
		//println(usr1)
		//func(4)
	}
}


