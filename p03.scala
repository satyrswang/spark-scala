/**
 * [参数只在类的()时需要var var x: , 为空时是否需要().只在需要分配时加，相当于new ]
 * @Author   WYQ
 * @DateTime 2017-09-24T05:46:17+0800
 * @return   {[type]}                 [description]
 */

//为什么hasNext不需要 这里就是一个Boolean值该值取决于一个function(注意不是method)
//def hasNext: Boolean = 3<3    val hasNext: Boolean = 3<3  没有区别Class[Boolean] = boolean

trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}//def f()={println("a")} f:()Unit 
// def f():String={"a"}  ()String   
//def f:String={"a"}   f: String    
//def f:String="a"
//val f:String="a"

//trait中[]不需要var b:,直接为类型 同样其中的函数()中的参数也不需var 但是必须有b:。不能只是类型名
//不需要参数时，是否()--未定

//class中参数需var，否则不能成为成员 不能用b.b获得
//Traits become especially useful as generic types and with abstract methods.
trait hair[ String] {
	def  hairCor( b:String) 
	//def hairLenth: Int //def 时无需().什么时候需要() ?
}		
class beauty(var b:String) extends hair[String]{

	//override时参数同样不能var
	override def hairCor(  b:String) = this.b match {
		case "yellow" => println("beauty" )
		case _ => println("not")
	}


}
//获得返回的string
object test2{
	def main(args:Array[String]){
		var b = new beauty("yellow")
		b.hairCor(b.b)  //getter	
	}

}

 
