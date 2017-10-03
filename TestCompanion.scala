/**
 * [companion communication]
 * @Author   WYQ
 * @DateTime 2017-09-24T02:35:25+0800
 * @param    {[type]}                 a [description]
 * @param    {[type]}                 b [description]
 * @return   {[type]}                   [description]
 */

object my{
	private var m:Int =1
	def display(){
		var o:my= new my  //get private var in class my
    		println("obmy"+o.c)
	}
}

class my{
	import my._
	private var c:Int =2
	def show(){
		println("calss"+my.m) //get the private var in object my
	}
}

object test{
	def main(args:Array[String]){
		var myclass:my = new my
		my.display()
		myclass.show()
	}
}
