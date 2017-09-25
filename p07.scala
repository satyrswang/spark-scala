package scalatestp01
//https://stackoverflow.com/questions/11889907/how-to-convert-json-to-a-type-in-scala

//abstract works with extends
//case works with extends abstract class , sons of abstract class
//class has parameters with types
//object JNull

abstract class JSON
case class JSeq(elems: List[JSON]) extends JSON
case class JNum(elems: Double) extends JSON
case class JStr(elems: String) extends JSON
case class JBool(elems: Boolean) extends JSON
case class JObj(bingdings: Map[String, JSON]) extends JSON
case object JNull extends JSON
type JBinding = (String , JSON)



// a match{case ab ; case ac ..}
//=> do what,instructions = is what,defination
//

class tjson {
  def show(json :JSON) :String= json match{
    case JNum(num) => num.toString
    case JStr(str) => "\""+str +"\""
    case JBool(b) => b.toString
    case JSeq(seq）=>"["+  (seq map show mkString  ",")  +"]"
    // binding is what :map from (k,v) to String 
    case JObj(bindings) => 
      val obj = bindings map{
        case (key ,value) =>"\"" +key+"\":" + show(value)
      }
    case JNull =>"null"
  } 
  
  
  val jadata = JObj(Map(
     "firstName" -> JStr("John"),
     "phoneNumbers"-> JSep(List(
         JObj(Map("type"->JStr("home") , "number"->JStr("2000 000 00"))
             )
         ))
     ))
     
  //The functional trait
/*     trait Function1[-A, +R]{
       def apply(x：A)：R
*/  }
  trait Function1[JBinding, String]
  // pattern matching block expands to Functionl instance
  
  new Function1 [JBinding, String]{
       def apply(x:JBinding): String = x match{
         case (key, value) => key + ":"+show(value)
         }
     }
  
  //subclassing functions
  // trait Map[k,v] extends (k,v)
  
  trait Seq[Elem] extends (Int =>Elem)
  
  
  
  
}
