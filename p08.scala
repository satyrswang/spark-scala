package scalatestp01

object test2 {
  println("Partial Function")
  
  val f:String => String ={case "p"=>"q"}
  def pafun(s：String) :String = { case "p"=>"q" }
  val f:PartialFunction[String, String ] ={case "p"=>"q" }
  f("p")
  f.isDefinedAt("p")
  
  //function as a parameter means the return type as parameter
  abstract class List[+T]{
    def map[U](f:T=>U) :List[U] = this match{
      case x::xs =>f(x) :: xs.map(f)
      case Nil => Nil   
  }
    def flatMap[U](f:T=> List[U]) :List[U] = this match{
      case x::xs =>f(x) ++ xs.map(f)
      case Nil => Nil   
  }
 } 
}
