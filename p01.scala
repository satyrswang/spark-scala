/**
 * [object 和 class通信]
 * @Author   WYQ
 * @DateTime 2017-09-24T02:35:25+0800
 * @param    {[type]}                 a [description]
 * @param    {[type]}                 b [description]
 * @return   {[type]}                   [description]
 */

object my{
	private var m:Int =1
	def display(){
		var o:my= new my //使读取class 中private var
    		println("obmy"+o.c)
	}
}

class my{
	import my._
	private var c:Int =2
	def show(){println("calss"+my.m)}
}

object test{
	def main(args:Array[String]){
		var cc:my = new my
		my.display()
		cc.show()
	}
}
