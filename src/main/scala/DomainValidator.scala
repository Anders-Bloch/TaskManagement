import java.text._
import java.util._
import scala.xml._

object DomainValidator {

  def datesInOrder(startDate : String, endDate : String ) : Boolean = {
    val df = new SimpleDateFormat("dd.MM.yyyy")
    df.parse(endDate) after df.parse(startDate) 	  
  }
	
  def isIterationDatesInConflict(project: NodeSeq, startDate : String, endDate: String) : Boolean = {
    val df = new SimpleDateFormat("dd.MM.yyyy")
	val start = df.parse(startDate)
	val end = df.parse(endDate)
	var conflict = false
	println(project \ "iteration")
	(project \ "iteration") foreach((n : Node) => {
	  val s = df.parse(n \ "@startDate" text)
	  val e = df.parse(n \ "@endDate" text)
	  if(start.getTime() > e.getTime() || end.getTime() < s.getTime()) {
		//Dates valid
	  } else {
		conflict = true
	  }
	})
	conflict
  }

}