/**
 * [main description]
 * @Author   WYQ
 * @DateTime 2017-09-25T14:40:27+0800
 * @return   {[type]}                 [description]
 */


def main(args:List[String]){

	import scala.util.matching.Regex

	val numberPattern: Regex = "[0-9]".r

	numberPattern.findFirstMatchIn("awesomepassword") match {
	  case Some(_) => println("Password OK")
	  case None => println("Password must contain a number")
	}
	import scala.util.matching.Regex

	val keyValPattern: Regex = "([0-9a-zA-Z-#() ]+): ([0-9a-zA-Z-#() ]+)".r

	val input: String =
	  """background-color: #A03300;
	    |background-image: url(img/header100.png);
	    |background-position: top center;
	    |background-repeat: repeat-x;
	    |background-size: 2160px 108px;
	    |margin: 0;
	    |height: 108px;
	    |width: 100%;""".stripMargin

	

	for (patternMatch <- keyValPattern.findAllMatchIn(input))
	  println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")

	// =======================
// service interfaces
  trait OnOffDevice {
    def on: Unit
    def off: Unit
  }
  trait SensorDevice {
    def isCoffeePresent: Boolean
  }

  // =======================
  // service implementations
  class Heater extends OnOffDevice {
    def on = println("heater.on")
    def off = println("heater.off")
  }
  class PotSensor extends SensorDevice {
    def isCoffeePresent = true
  }

  // =======================
  // service declaring two dependencies that it wants injected
  class Warmer(
    implicit val sensor: SensorDevice,
    implicit val onOff: OnOffDevice) {

    def trigger = {
      if (sensor.isCoffeePresent) onOff.on
      else onOff.off
    }
  }

  // =======================
  // instantiate the services in a module
  object Services {
    implicit val potSensor = new PotSensor
    implicit val heater = new Heater
  }

  // =======================
  // import the services into the current scope and the wiring
  // is done automatically using the implicits
  import Services._

  val warmer = new Warmer
  warmer.trigger

}


