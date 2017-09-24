/**
 * Traits become especially useful as generic types and with abstract methods
 * Methods are defined with the def keyword. def is followed by a name, parameter lists, a return type, and a body.
 * @Author   WYQ
 * @DateTime 2017-09-24T05:46:17+0800
 * @return   {[type]}                 [description]
 */

//trait中[]不需要var b:,直接为类型 同样其中的函数()中的参数也不需var 但是必须有b:。不能只是类型名
trait Iterator[A] {
  def hasNext: Boolean // var hasNext: Boolean
  def next(): A //method
}
//def f()={println("a")} f:()Unit 
// def f():String={"a"}  ()String   
//def f:String={"a"}   f: String    
//def f:String="a"
//val f:String="a"  

//def hasNext: Boolean = 3<3    val hasNext: Boolean = 3<3  一样
class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0
  override def hasNext: Boolean = current < to
  override def next(): Int =  {
    if (hasNext) {
      val t = current
      current += 1
      println(t)
      t
    } else 0
  }
}

//class中参数需var，否则 不能用b.b获得
trait hair[String] {
	def  hairCor(b:String) 
}		
class beauty(var b:String) extends hair[String]{

	//override时参数同样不能var
	override def hairCor(b:String) = this.b match {
		case "yellow" => println("beauty" )
		case _ => println("not")
	}
}

object test2{
	def main(args:Array[String]){
		var b = new beauty("yellow")
		b.hairCor(b.b)  //getter	
		val addOne = (x: Int) => x + 1
		println(addOne(1)) // 2
		val iterator = new IntIterator(10)
		iterator.next()  // prints 0
		iterator.next()  // prints 1
	}

}

 
