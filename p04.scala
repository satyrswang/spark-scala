/**
 * [main description]
 * @Author   WYQ
 * @DateTime 2017-09-24T07:48:08+0800
 * @return   {[type]}                 [description]
 */
object test{
	def main(args:Array[String]){
		val s   = new si("woshi")
		s.next

		//这里如果不是args(0)该如何表示从外部传入参数？则无法表示
		class mi(val dd :String) extends si( args(0)) with Ri{
			val m =new mi(s)
			m foreach println

}

		val n = new mi(s.s_)

		// val textFile = "hello wang /n yuqing yu /n qing he wang qing/n hi"
		// val counts = textFile.flatMap(line => line.split(" "))
  //                .map(word => (word, 1))
  //                .reduceByKey(_ + _)
  //       println(counts)

	}

}

abstract class AbsIterator {
  type T
  def hasNext: Boolean
  def next(): T
}//声明

trait Ri extends AbsIterator{
	def foreach(f:T=>Unit):Unit = while(hasNext) f(next())
}//foreach的参数是函数f f(next())因为next()返回参数为T
//next()返回值作为参数继续传递给f

class si(val s:String) extends AbsIterator{
	def s_ = s
	type T = Char
	private var i = 0
  def hasNext = i < s.length //just a Boolean
  def next() = {
    val ch = s charAt i //s.charAt(i)
    i += 1
    println(ch)
    ch //return T
	}
}


