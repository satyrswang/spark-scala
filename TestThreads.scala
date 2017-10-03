/**
 * [synchronize in scala]
 * @Author   WYQ
 * @DateTime 2017-10-01T20:25:50+0800
 * @return   {[type]}                 [description]
 */

object Sop{def print(s:String)=println(s+"\n")}
//class theDe{
   def test(s:String) = this.synchronized {
    (1 until 5)map(a=>Sop.print(s + s"$a"))
  }
//}

class Threadt(private var name:String/*,private var thede:theDe*/) extends Thread{
   // var name:String
   // var thede:theDe
  def apply={
    // this.name = name
    // this.thede = thede
    start();
  }
  
  override def run()=test(name)
    //thede.test(name)
}

def main(args:Array[String]){
  // var thede = new theDe
  // new Threadt("T 1",thede);
  // new Threadt("T 2",thede);
  // new Threadt("T 3",thede);
  new Threadt("t1")
  new Threadt("t2")
  new Threadt("t3")
}
