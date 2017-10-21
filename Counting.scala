package examples

object Counting extends App {
  val numThread = 3
  val handOff = Array.fill(numThread)(false)
  val pool =  Array.tabulate(numThread)(i =>new Thread {
    override def run:Unit = {
      println("start " + i)
    //当未被打开时 保持wait
      Counting.synchronized{
        for(j <- 1 to 5){
         while(!handOff(i)){
            Counting.wait()
      }
         handOff(i) = false
         println(i+ " : " +j)
         handOff((i + 1)%numThread) = true //当i =1 则 2，2则0，0则1
         Counting.notifyAll()
         
      }
      }      
    }
  })

  
  pool.foreach(_.start())
  Thread.sleep(1000)
  println("first notify")
  handOff(0)=true  
  synchronized{notifyAll()}
  
  
  
}