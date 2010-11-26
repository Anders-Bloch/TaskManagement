import java.text._
import java.util._

object DomainValidator {

	def datesInOrder(startDate : String, endDate : String ) : Boolean = {
	  val df = new SimpleDateFormat("dd.MM.yyyy")
	  df.parse(endDate) after df.parse(startDate) 	  
	}

}