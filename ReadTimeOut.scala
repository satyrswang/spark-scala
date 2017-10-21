package examples

import io.StdIn._
object ReadTimeOut extends App {
  var answered  = false
  var timeUp = false
  var t = new Thread{
    override def run:Unit = {
      var i = 10
      while (!answered && i>0){ 
        println(i)
        Thread.sleep(1000)
        i-=1
      }
      
      if(i == 0)
      
      { 
        println("timeout")
        timeUp = true
      }
        
      
//      if(answered){
//        println("stop the time")
//        sys.exit(0)
//        
//    }
    
    }
    
  }
  
  println("enter your age")
  t.start()
//  if(!Console.in.ready()) Thread.sleep(100)
    while (!timeUp && !Console.in.ready()) {
    Thread.sleep(10)
  }
  if(!timeUp)
  {
    val age = readInt
  answered = true
  if(age < 18) println("no")
  else println("ok")
  }
  
  
}