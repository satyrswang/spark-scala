/**
 * [main description]
 * @Author   WYQ
 * @DateTime 2017-09-30T04:19:45+0800
 * @return   {[type]}                 [description]
 */

object tmp{
  class IntPir(val x: Int, val y: Int){

    import math.Ordering
    //def apply( x: Int , y: Int )={var x: Int=1 ;var y: Int=2 }
    implicit def ipord: Ordering[IntPir] =
      Ordering.by(IntPir => (this.x, this.y))
    
    class test(p1:Int =1,val p2:Int = 2,var p3:Int=3, var p4:Int){
      private var te = 99
      def getp1 =println(p1)
      def getp2=println(p2)
      def getp3=println(p3)
      def getp4=println(p4)
    //  def getpe=println(te)
    }
   // object test{ import test._  ; var h = new test(99,8,7,6) ; def pee = println(h.te)}
    var hi= new test(99,8,7,6)

    class e extends test(5,6,7,3){}

}
// trait ob{object c}
// trait ob {class a}
// trait ob{trait c}


// object ob{object c}
// object sc {trait a}
// object ob {class a}

// def ob= {class a}
// var ob ={class a}

// class m{trait c}
//都可互相包含


def main(args:Array[String])={
  
  
  val b = new IntPir (3,4)
  println(b.x,b.y)
  val cintp =  new IntPir(5,6)
  val d = new  IntPir(6,5)
  println(cintp.ipord.lt(b,d))
  println(cintp.ipord.compare(b,d))//必须要有class。而可以没有object 只需要new则可获得value，但无class则无type
  println(b.hi.te)
  var t = new cintp.e
   t.getp1
   t.getp2
   t.getp3
   t.getp4
  // t.pe

   var mmm = new cintp.test(3,1,1,2)
   var mo = cintp.test
   mmm.getp4
   mmm.getp3
   mmm.getp2 //all this don't have to have an object then still can access data 
   mmm.getp1
  // println(mo.pe)
   //mo.getp1
  // mo.pee// must have an object to access the private data

}
}


